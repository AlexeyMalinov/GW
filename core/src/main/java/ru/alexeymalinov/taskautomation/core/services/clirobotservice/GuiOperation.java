package ru.alexeymalinov.taskautomation.core.services.clirobotservice;

import ru.alexeymalinov.taskautomation.core.services.Operation;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.function.BiFunction;

public enum GuiOperation implements Operation<Robot> {
    POINT(GuiOperation::actionPoint),
    LEFT_CLICK(GuiOperation::actionLeftClick),
    LEFT_PRESS(GuiOperation::actionLeftPress),
    LEFT_RELEASE(GuiOperation::actionLeftRelease),
    RIGHT_CLICK(GuiOperation::actionRightClick),
    RIGHT_PRESS(GuiOperation::actionRightPress),
    RIGHT_RELEASE(GuiOperation::actionRightRelease),
    SLEEP(GuiOperation::actionSleep),
    DOUBLE_CLICK(GuiOperation::actionDoubleClick),
    PRESS_KEY(GuiOperation::actionPressKey),
    ENTER_TEXT(GuiOperation::actionEnterText);

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

    private static String actionLeftClick(Robot robot, String s){
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        return "left-clicked";
    }

    private static String actionLeftPress(Robot robot, String s){
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        return "left mouse button pressed";
    }

    private static String actionLeftRelease(Robot robot, String s){
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        return "left mouse button released ";
    }
    private static String actionRightClick(Robot robot, String s){
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        return "left-clicked";
    }

    private static String actionRightPress(Robot robot, String s){
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        return "left mouse button pressed";
    }

    private static String actionRightRelease(Robot robot, String s){
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        return "left mouse button released ";
    }

    private static String actionDoubleClick(Robot robot, String s){
        actionLeftClick(robot, s);
        actionLeftClick(robot,s);
        return "left-double-clicked";
    }

    private static String actionSleep(Robot robot, String s){
        try {
            Thread.sleep(Long.parseLong(s));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "sleep" + s + "mills";
    }

    private static String actionPressKey(Robot robot, String s){
        //TODO implement method
        throw new IllegalStateException("Unsupported operation");
    }

    private static String actionEnterText(Robot robot, String s){
        //TODO implement method
        throw new IllegalStateException("Unsupported operation");
    }
}
