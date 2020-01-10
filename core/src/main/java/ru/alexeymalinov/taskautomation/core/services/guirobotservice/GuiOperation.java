package ru.alexeymalinov.taskautomation.core.services.guirobotservice;

import ru.alexeymalinov.taskautomation.core.services.Operation;

import java.awt.*;
import java.awt.event.InputEvent;
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

    BiFunction<Robot, String, String> action;

    GuiOperation(BiFunction<Robot, String, String> action) {
        this.action = action;
    }


    @Override
    public String apply(Robot robot, String s) {
        return action.apply(robot,s);
    }

    private static String actionPoint(Robot robot, String s){
        int x = Integer.parseInt(s.split(";")[0]);
        int y = Integer.parseInt(s.split(";")[0]);
        robot.mouseMove(x,y);
        return "The cursor has moved to a point with coordinates " +
                "x = " +
                x +
                ", " +
                "y = " +
                y;
    }

    private static String leftButtonClick(Robot robot, String s){
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        return "left-clicked";
    }

    private static String leftButtonPress(Robot robot, String s){
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        return "left mouse button pressed";
    }

    private static String leftButtonRelease(Robot robot, String s){
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        return "left mouse button released ";
    }
    private static String rightButtonClick(Robot robot, String s){
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        return "left-clicked";
    }

    private static String rightButtonPress(Robot robot, String s){
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        return "left mouse button pressed";
    }

    private static String rightButtonRelease(Robot robot, String s){
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        return "left mouse button released ";
    }

    private static String leftButtonDoubleClick(Robot robot, String s){
        leftButtonClick(robot, s);
        leftButtonClick(robot,s);
        return "left-double-clicked";
    }

    private static String sleep(Robot robot, String s){
        try {
            Thread.sleep(Long.parseLong(s));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "sleep" + s + "mills";
    }

    private static String pressKey(Robot robot, String s){
        //TODO implement method
        throw new IllegalStateException("Unsupported operation");
    }

    private static String enterText(Robot robot, String s){
        //TODO implement method
        throw new IllegalStateException("Unsupported operation");
    }
}
