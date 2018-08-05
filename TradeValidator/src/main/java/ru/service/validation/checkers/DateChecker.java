package ru.service.validation.checkers;

import org.apache.log4j.Logger;
import ru.service.enums.OptionStyle;
import ru.service.enums.StatusCode;
import ru.service.messages.CheckResponse;
import ru.domain.Trade;
import ru.service.utils.ErrorMassages;
import ru.service.utils.HolidaysParser;
import ru.service.utils.TradeTypeHelper;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

/**
 * Checker for date fields. At the moment they are: ccyPair, payCcy, premiumCcy.
 */
public class DateChecker implements Checker {
    private final Logger logger = Logger.getLogger(DateChecker.class);
    private final HolidaysParser holidaysParser = new HolidaysParser();
    private final HashSet<Calendar> holidays = new HashSet<>();
    private final int spotValueDate = 2;
    private final int forwardValueDate = 5;

    /**
     * Get holidays from resources folder.
     */
    @Override
    public void init() {
        holidays.addAll(holidaysParser.parse("holidays.xml"));
    }

    /**
     * Sets common check flow.
     *
     * @param trade    to check.
     * @param response response where to store result.
     * @return
     */
    @Override
    public CheckResponse check(Trade trade, CheckResponse response) {
        if (isAllDateFieldsExist(trade, response)) {
            checkValueDateBeforeTradeDate(trade, response);
            checkValueDateIsWorking(trade, response);
            if (trade.getType() == null) {
                response.setStatusCode(StatusCode.ERROR);
                response.addMessage(ErrorMassages.EMPTY_INSTRUMENT_TYPE);
                logger.info(ErrorMassages.EMPTY_INSTRUMENT_TYPE);
            } else if (TradeTypeHelper.isVanilla(trade.getType())) {
                checkExpiryPremiumDate(trade, response);
                if (trade.getStyle() == null) {
                    response.setStatusCode(StatusCode.ERROR);
                    response.addMessage(ErrorMassages.EMPTY_STYLE);
                    logger.info(ErrorMassages.EMPTY_STYLE);
                } else if (trade.getStyle().toUpperCase().equals(OptionStyle.AMERICAN.name())) {
                    checkExcerciseStartDate(trade, response);
                }
            } else if (TradeTypeHelper.isSpot(trade.getType())) {
                checkValueDateWorkingDays(trade, response, spotValueDate);
            } else if (TradeTypeHelper.isForward(trade.getType())) {
                checkValueDateWorkingDays(trade, response, forwardValueDate);
            }
        }
        return response;
    }

    /**
     * Checks if all necessary date fields are exist. If not -  there will be error for every field.
     * For all trades it is needed to have trade date and value date.
     * For options it is needed to have additional fields:
     * expiry date, excercise start date, premium date, delivery date.
     *
     * @param trade
     * @param response
     * @return
     */
    private boolean isAllDateFieldsExist(Trade trade, CheckResponse response) {
        boolean result = true;
        if (trade.getTradeDate() == null) {
            response.setStatusCode(StatusCode.ERROR);
            response.addMessage(ErrorMassages.EMPTY_TRADE_DATE);
            logger.info(ErrorMassages.EMPTY_TRADE_DATE);
            result = false;
        }
        if (trade.getValueDate() == null) {
            response.setStatusCode(StatusCode.ERROR);
            response.addMessage(ErrorMassages.EMPTY_VALUE_DATE);
            logger.info(ErrorMassages.EMPTY_VALUE_DATE);
            result = false;
        }
        if (trade.getType() != null && TradeTypeHelper.isVanilla(trade.getType())) {
            if (trade.getExpiryDate() == null) {
                response.setStatusCode(StatusCode.ERROR);
                response.addMessage(ErrorMassages.EMPTY_EXPIRY_DATE);
                logger.info(ErrorMassages.EMPTY_EXPIRY_DATE);
                result = false;
            }
            if (trade.getExcerciseStartDate() == null) {
                response.setStatusCode(StatusCode.ERROR);
                response.addMessage(ErrorMassages.EMPTY_EXERCISE_DATE);
                logger.info(ErrorMassages.EMPTY_EXERCISE_DATE);
                result = false;
            }
            if (trade.getPremiumDate() == null) {
                response.setStatusCode(StatusCode.ERROR);
                response.addMessage(ErrorMassages.EMPTY_PREMIUM_DATE);
                logger.info(ErrorMassages.EMPTY_PREMIUM_DATE);
                result = false;
            }
            if (trade.getDeliveryDate() == null) {
                response.setStatusCode(StatusCode.ERROR);
                response.addMessage(ErrorMassages.EMPTY_DELIVERY_DATE);
                logger.info(ErrorMassages.EMPTY_DELIVERY_DATE);
                result = false;
            }
        }
        return result;
    }

    /**
     * Calculates valueDate to be and compares it with actual. If they are no equal - error.
     *
     * @param trade    - trade to check.
     * @param response - response to add checking result.
     * @param days     - working days.
     */
    private void checkValueDateWorkingDays(Trade trade, CheckResponse response, int days) {
        Date tradeDate = trade.getTradeDate();
        Date valueDate = trade.getValueDate();
        Date valueDateToBe = calculateEndDate(tradeDate, days);
        logger.info("Value date should be: " + valueDateToBe);
        if (!valueDate.equals(valueDateToBe)) {
            response.setStatusCode(StatusCode.ERROR);
            String message = String.format("Value date is not %d working days from Trade date.", days);
            response.addMessage(message);
            logger.info(message);
        }
    }

    /**
     * Calculates working days from trade date.
     *
     * @param tradeDate where to start from.
     * @param duration  - working days.
     * @return date after calculation.
     */
    public Date calculateEndDate(Date tradeDate, int duration) {
        Calendar startDate = Calendar.getInstance();
        startDate.setTimeInMillis(tradeDate.getTime());
        for (int i = 0; i < duration; i++) {
            startDate.add(Calendar.DAY_OF_MONTH, 1);
            while (!isWorking(startDate)) {
                startDate.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        return startDate.getTime();
    }

    /**
     * Value date cannot be before trade date.
     *
     * @param trade    - trade to check.
     * @param response - response to add checking result.
     */
    private void checkValueDateBeforeTradeDate(Trade trade, CheckResponse response) {
        if (trade.getValueDate().before(trade.getTradeDate())) {
            response.setStatusCode(StatusCode.ERROR);
            String message = "Value date cannot be before Trade date.";
            response.addMessage(message);
            logger.info(message);
        }
    }

    /**
     * Value date cannot fall on weekend or non-working day.
     *
     * @param trade    - trade to check.
     * @param response - response to add checking result.
     */
    private void checkValueDateIsWorking(Trade trade, CheckResponse response) {
        Calendar valueDate = Calendar.getInstance();
        valueDate.setTime(trade.getValueDate());
        if (!isWorking(valueDate)) {
            response.setStatusCode(StatusCode.ERROR);
            String message = "Value date cannot be weekend or holiday.";
            response.addMessage(message);
            logger.info(message);
        }
    }

    /**
     * Option specific: expiry date and premium date shall be before delivery date.
     *
     * @param trade    - trade to check.
     * @param response - response to add checking result.
     */
    private void checkExpiryPremiumDate(Trade trade, CheckResponse response) {
        Date deliveryDate = trade.getDeliveryDate();
        Date premiumDate = trade.getPremiumDate();
        Date expiryDate = trade.getExpiryDate();
        if (!expiryDate.before(deliveryDate) || !premiumDate.before(deliveryDate)) {
            response.setStatusCode(StatusCode.ERROR);
            String message = "'Expiry date' and 'Premium date' shall be before 'Delivery date'.";
            response.addMessage(message);
            logger.info(message);
        }

    }

    /**
     * American option specific: american option style will have in addition
     * the exercise Star tDate, which has to be after the trade date but before the expiry date.
     *
     * @param trade    - trade to check.
     * @param response - response to add checking result.
     */
    private void checkExcerciseStartDate(Trade trade, CheckResponse response) {
        Date exerciseStartDate = trade.getExcerciseStartDate();
        Date tradeDate = trade.getTradeDate();
        Date expiryDate = trade.getExpiryDate();
        if (expiryDate.before(exerciseStartDate) || exerciseStartDate.before(tradeDate)) {
            response.setStatusCode(StatusCode.ERROR);
            String message = "'Exercise Start Date' has to be after the 'Trade date' but before the 'Expiry date'.";
            response.addMessage(message);
            logger.info(message);
        }
    }

    /**
     * Checks id day is working day (should not be weekend and holiday).
     *
     * @param date to check.
     * @return true is day is working.
     */
    private boolean isWorking(Calendar date) {
        return !isHoliday(date, holidays) && !isWeekend(date);
    }

    /**
     * Checks if date is a holiday.
     *
     * @param date     to check.
     * @param holidays - collection of holidays.
     * @return true if date is holiday.
     */
    private boolean isHoliday(Calendar date, Collection<Calendar> holidays) {
        boolean result = false;
        for (Calendar cal : holidays) {
            if (cal.get(Calendar.MONTH) == date.get(Calendar.MONTH)
                    && cal.get(Calendar.DATE) == date.get(Calendar.DATE)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Checks if date is a saturday or sunday.
     *
     * @param date to check.
     * @return true if date is weekend.
     */
    private boolean isWeekend(Calendar date) {
        boolean result = false;
        if (date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || date.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            result = true;
        }
        return result;
    }
}
