/**
 * Created on Feb.11 for SWE261P project.
 */
package org.terasology.logic.console.ui;

import org.junit.Before;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.FieldSetter;
import org.terasology.TerasologyTestingEnvironment;

import org.mockito.*;
//import org.powermock.modules.junit4.*;



import org.terasology.rendering.nui.UIWidget;
import org.terasology.rendering.nui.widgets.UILabel;


import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * All test cases are executed in order to make the state machine simulating process cleaner.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@RunWith(PowerMockRunner.class)
public class NotificationOverlayTest extends TerasologyTestingEnvironment {

    private enum State {
        FADE_IN,
        VISIBLE,
        FADE_OUT,
        HIDDEN
    }

    // Notification depends on abstract class AbstractWidget, while its method setVisible invokes the super method.
    // Thus we mock everything in the super method call.
    NotificationOverlay spy = Mockito.spy(new NotificationOverlay());

    @Before
    public void setup(){
        //overlay = new NotificationOverlay();
        //overlay.initialise();

        spy = new NotificationOverlay();

    }
    /**
     * On setVisible(true), refresh() should be called and the state should transit to FADE_IN if it was previously HIDDEN
     */
    @Test
    @Order(1)
    public void hiddenNoChangeAndToFadeIn(){
        spy.update(10);
        assertEquals(spy.getStateValue(), State.HIDDEN.toString());     // no change should occur on update() when state is HIDDEN.
        spy.setVisible(true);
        // we compare state strings directly.
        assertEquals(spy.getStateValue(), State.FADE_IN.toString());
    }

    /**
     * On setVisible(false), refresh() should be called and the state should be set to HIDDEN.
     * Note that time is set to 0 on any state changes.
     */
    @Test
    @Order(2)
    public void whatEverToHIDDEN(){
        spy.setVisible(false);
        // we compare state strings directly.
        assertEquals(spy.getStateValue(), State.HIDDEN.toString());
        spy.setState("VISIBLE");
        spy.setVisible(false);
        assertEquals(spy.getStateValue(), State.HIDDEN.toString());
        spy.setState(("FADE_OUT"));
        spy.setVisible(false);
        assertEquals(spy.getStateValue(), State.HIDDEN.toString());
    }

    /**
     * FADE_IN goes to VISIBLE when time+delta > TIME_FADE on call to update(delta).
     * time is currently 0. Note that time is set to 0 on any state changes.
     *
     */
    @Test
    @Order(3)
    public void fadeInNoChangeAndToVisible(){
        spy.setVisible(true);       // goes to fade_in
        spy.setVisible(true);       // duplicate calls has no further effect
        assertEquals(spy.getStateValue(), State.FADE_IN.toString());
        //time is currently 0. fade_in goes to visible when time+delta > TIME_FADE on call to update(delta).
        spy.update(0.1f);
        assertEquals(spy.getStateValue(), State.FADE_IN.toString());    // time + delta < TIME_FADE, no change in state
        spy.update(0.5f);
        assertEquals(spy.getStateValue(), State.VISIBLE.toString());    // 0.5 > TIME_FADE, which is 0.3
        System.out.println(spy.getTime() + " " + spy.getStateValue());
    }

    /**
     * VISIBLE should go to FADE_IN when time+delta > maxTime on update(delta) call. No change otherwise.
     * maxTime is set to be TIME_VISIBLE_BASE + textLen * TIME_VISIBLE_PER_CHAR;
     * Since textLen is impossible to be got, maxTime should be equal to only TIME_VISIBLE_BASE, which is 5.
     * Note that time is set to 0 on any state changes.
     *
     */
//    @Test
//    @Order(4)
//    public void visibleNoChangeAndToFadeOut(){
//
//        try{
//            FieldSetter.setField(spy, spy.getClass().getDeclaredField("message"), Mockito.mock(UILabel.class));
//        }catch(NoSuchFieldException e){
//            System.out.println(e);
//        }
//        spy.setVisible(true);
//        spy.setState("VISIBLE");
//        spy.setVisible(false);
//        assertEquals(spy.getStateValue(), State.HIDDEN.toString());     //should go back to hidden directly
//        spy.setState("VISIBLE");
//        spy.update(1);
//        assertEquals(spy.getStateValue(), State.VISIBLE.toString());
//        spy.update(10);
//        assertEquals(spy.getStateValue(), State.VISIBLE.toString());    // time + delta  <= maxTime, no change in state
//        spy.update(5);
//        assertEquals(spy.getStateValue(), State.FADE_OUT.toString());    // time + delta > maxTime, which is 5
//    }


    /**
     * A call to setVisible(true) will set FADE_OUT to FADE_IN.
     * update(delta) will set FADE_OUT to HIDDEN if time+delta > TIME_FADE. No actions made otherwise.
     */
    @Test
    @Order(5)
    public void visibleToHiddenAndFadeIn(){
        spy.setState("FADE_OUT");
        assertEquals(spy.getStateValue(), State.FADE_OUT.toString());
        spy.update(0.1f);
        assertEquals(spy.getStateValue(), State.FADE_OUT.toString());
        spy.update(0.4f);
        assertEquals(spy.getStateValue(), State.HIDDEN.toString());         //time > TIME_FADE, back to HIDDEN.

        spy.setState("FADE_OUT");
        spy.setVisible(true);
        assertEquals(spy.getStateValue(), State.FADE_IN.toString());
    }


}