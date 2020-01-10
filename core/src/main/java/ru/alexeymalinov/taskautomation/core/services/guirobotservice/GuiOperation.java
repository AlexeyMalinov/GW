package ru.alexeymalinov.taskautomation.core.services.guirobotservice;

import ru.alexeymalinov.taskautomation.core.services.Operation;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.function.BiFunction;

public enum GuiOperation implements Operation<Robot> {

    POINT(GuiOperation::actionPoint, "point"),
    LEFT_CLICK(GuiOperation::leftButtonClick, "left_click"),
    LEFT_PRESS(GuiOperation::leftButtonPress, "left_press"),
    LEFT_RELEASE(GuiOperation::leftButtonRelease, "left_release"),
    RIGHT_CLICK(GuiOperation::rightButtonClick, "right_click"),
    RIGHT_PRESS(GuiOperation::rightButtonPress, "right_press"),
    RIGHT_RELEASE(GuiOperation::rightButtonRelease, "right_release"),
    SLEEP(GuiOperation::sleep, "sleep"),
    DOUBLE_CLICK(GuiOperation::leftButtonDoubleClick, "double_click"),
    PRESS_KEY(GuiOperation::pressKey, "press_key"),
    ENTER_TEXT(GuiOperation::enterText, "enter_text");

    private final BiFunction<Robot, String, String> action;
    private final String name;

    GuiOperation(BiFunction<Robot, String, String> action, String name) {
        this.action = action;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String apply(Robot robot, String s) {
        return action.apply(robot, s);
    }

    private static String actionPoint(Robot robot, String s) {
        int x = Integer.parseInt(s.split(";")[0]);
        int y = Integer.parseInt(s.split(";")[0]);
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
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        return "left-clicked";
    }

    private static String rightButtonPress(Robot robot, String s) {
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        return "left mouse button pressed";
    }

    private static String rightButtonRelease(Robot robot, String s) {
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
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
        //TODO implement method
        throw new IllegalStateException("Unsupported operation");
    }

    private static String enterText(Robot robot, String s) {
        //TODO implement method
        throw new IllegalStateException("Unsupported operation");
    }

}
