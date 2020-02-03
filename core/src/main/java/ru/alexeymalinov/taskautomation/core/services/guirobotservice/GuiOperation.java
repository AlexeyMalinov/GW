package ru.alexeymalinov.taskautomation.core.services.guirobotservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.alexeymalinov.taskautomation.core.services.Operation;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public enum GuiOperation implements Operation<Robot> {


    POINT(GuiOperation::actionPoint),
    LEFT_CLICK(GuiOperation::leftButtonClick),
    LEFT_PRESS(GuiOperation::leftButtonPress),
    LEFT_RELEASE(GuiOperation::leftButtonRelease),
    RIGHT_CLICK(GuiOperation::rightButtonClick),
    RIGHT_PRESS(GuiOperation::rightButtonPress),
    RIGHT_RELEASE(GuiOperation::rightButtonRelease),
    SLEEP(GuiOperation::sleep),
    DOUBLE_CLICK(GuiOperation::leftButtonDoubleClick),
    PRESS_KEY(GuiOperation::pressKey),
    ENTER_TEXT(GuiOperation::enterText);

    private static Logger LOGGER = LoggerFactory.getLogger(GuiOperation.class);
    private final BiFunction<Robot, String, String> action;

    GuiOperation(BiFunction<Robot, String, String> action) {
        this.action = action;
    }

    @Override
    public String apply(Robot robot, String s) {
        return action.apply(robot, s);
    }

    private static String actionPoint(Robot robot, String s) {
        int x = Integer.parseInt(s.split(";")[0]);
        int y = Integer.parseInt(s.split(";")[1]);
        robot.mouseMove(x, y);
        return "The cursor has moved to a point with coordinates " +
                "x = " +
                x +
                ", " +
                "y = " +
                y;
    }

    private static String leftButtonClick(Robot robot, String s) {
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        return "left-clicked";
    }

    private static String leftButtonPress(Robot robot, String s) {
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        return "left mouse button pressed";
    }

    private static String leftButtonRelease(Robot robot, String s) {
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        return "left mouse button released ";
    }

    private static String rightButtonClick(Robot robot, String s) {
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        return "left-clicked";
    }

    private static String rightButtonPress(Robot robot, String s) {
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        return "left mouse button pressed";
    }

    private static String rightButtonRelease(Robot robot, String s) {
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        return "left mouse button released ";
    }

    private static String leftButtonDoubleClick(Robot robot, String s) {
        leftButtonClick(robot, s);
        leftButtonClick(robot, s);
        return "left-double-clicked";
    }

    private static String sleep(Robot robot, String s) {
        try {
            Thread.sleep(Long.parseLong(s));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "sleep" + s + "mills";
    }

    private static String pressKey(Robot robot, String s) {
        robot.keyPress(KeyBoardKey.valueOf(s).getCode());
        return "";
    }

    private static String enterText(Robot robot, String s) {
        try {
            List<Integer> codeList = textToKeyEvent(s);
            for (Integer integer : codeList) {
                robot.keyPress(integer);
            }
        } catch (NoSuchFieldException e) {
            LOGGER.error("The character cannot be recognized", e);
        } catch (IllegalAccessException e) {
            LOGGER.error("EventKey class not available", e);
        }
        return "Enter" + s;
    }

    @Override
    public String getName() {
        return this.name();
    }


    private static List<Integer> textToKeyEvent(String string) throws NoSuchFieldException, IllegalAccessException {
        List<Integer> codeList = new ArrayList<>();
        String[] line = string.split(System.lineSeparator());
        for (int i = 0; i < line.length; i++) {
            codeList.addAll(stringToKeyEvent(line[i]));
            if (i != line.length - 1) {
                codeList.add(KeyEvent.VK_ENTER);
            }
        }
        return codeList;
    }

    private static List<Integer> stringToKeyEvent(String string) throws NoSuchFieldException, IllegalAccessException {
        List<Integer> codeList = new ArrayList<>();
        String[] words = string.split(" ");
        for (int i = 0; i < words.length; i++) {
            codeList.addAll(wordToKeyEvent(words[i]));
            if (i != words.length - 1) {
                codeList.add(KeyEvent.VK_SPACE);
            }
        }
        return codeList;
    }

    private static List<Integer> wordToKeyEvent(String word) throws NoSuchFieldException, IllegalAccessException {
        List<Integer> codeList = new ArrayList<>();
        for (char c : word.toUpperCase().toCharArray()) {
            codeList.add(charToKeyEvent(c));
        }
        return codeList;
    }

    private static int charToKeyEvent(char c) throws NoSuchFieldException, IllegalAccessException {
        return KeyEvent.class.getField("VK_" + c).getInt(null);
    }

}
