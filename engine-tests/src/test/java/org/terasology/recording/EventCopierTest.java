package org.terasology.recording;

import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.terasology.audio.events.PlaySoundEvent;
import org.terasology.engine.SimpleUri;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.Event;
import org.terasology.input.*;
import org.terasology.input.cameraTarget.CameraTargetChangedEvent;
import org.terasology.input.events.*;
import org.terasology.logic.characters.CharacterMoveInputEvent;
import org.terasology.logic.characters.GetMaxSpeedEvent;
import org.terasology.logic.characters.MovementMode;
import org.terasology.logic.characters.events.AttackEvent;
import org.terasology.math.geom.Vector2i;
import org.terasology.math.geom.Vector3f;
import org.terasology.math.geom.Vector3i;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

public class EventCopierTest {

    EventCopier e = new EventCopier();

    @Test
    public void copyPlaySoundEventTest() {
        PlaySoundEvent mockedClass = mock(PlaySoundEvent.class);

        assertEquals(mockedClass, e.copyEvent(mockedClass));
    }

    @Test
    public void copyBindButtonEventTest() {
        BindButtonEvent mockedClass = mock(BindButtonEvent.class);

        SimpleUri mockedId = mock(SimpleUri.class);
        when(mockedClass.getId()).thenReturn(mockedId);
        ButtonState mockedState = ButtonState.DOWN;

        assertEquals(true, e.copyEvent(mockedClass) instanceof BindButtonEvent);

        BindButtonEvent newEvent = (BindButtonEvent) e.copyEvent(mockedClass);
        assertEquals(mockedClass.getId(), newEvent.getId());
        assertEquals(mockedClass.getState(), newEvent.getState());
    }

    @Test
    public void copyKeyEventTest() {
        KeyEvent mockedClass = mock(KeyDownEvent.class);

        Input mockInput = mock(Input.class);
        when(mockedClass.getKey()).thenReturn(mockInput);
        char keyChar = 'k';
        when(mockedClass.getKeyCharacter()).thenReturn(keyChar);
        ButtonState buttonState = ButtonState.DOWN;
        when(mockedClass.getState()).thenReturn(buttonState);

        assertEquals(true, e.copyEvent(mockedClass) instanceof KeyEvent);

        KeyEvent newEvent = (KeyEvent) e.copyEvent(mockedClass);
        assertEquals(mockedClass.getKey(), newEvent.getKey());
        assertEquals(mockedClass.getState(), newEvent.getState());
        assertEquals(mockedClass.getKeyCharacter(), newEvent.getKeyCharacter());
    }

    @Test
    public void copyBindAxisEventTest() {
        BindAxisEvent mockedClass = mock(BindAxisEvent.class);

        String id = "id";
        when(mockedClass.getId()).thenReturn(id);
        float value = 1f;
        when(mockedClass.getValue()).thenReturn(value);

        assertEquals(true, e.copyEvent(mockedClass) instanceof BindAxisEvent);

        BindAxisEvent newEvent = (BindAxisEvent) e.copyEvent(mockedClass);
        assertEquals(mockedClass.getValue(), newEvent.getValue());
        assertEquals(mockedClass.getId(), newEvent.getId());
    }

    @Test
    public void copyMouseAxisEventTest() {
        MouseAxisEvent mockedClass = mock(MouseAxisEvent.class);

        float value = 1f;
        when(mockedClass.getValue()).thenReturn(value);
        MouseAxisEvent.MouseAxis mouseAxis = MouseAxisEvent.MouseAxis.X;
        when(mockedClass.getMouseAxis()).thenReturn(mouseAxis);

        assertEquals(true, e.copyEvent(mockedClass) instanceof MouseAxisEvent);

        MouseAxisEvent newEvent = (MouseAxisEvent) e.copyEvent(mockedClass);
        assertEquals(mockedClass.getValue(), newEvent.getValue());
        assertEquals(mockedClass.getMouseAxis(), newEvent.getMouseAxis());
    }

    @Test
    public void copyCameraTargetChangedEventTest() {
        CameraTargetChangedEvent mockedClass = mock(CameraTargetChangedEvent.class);

        EntityRef oldTarget = mock(EntityRef.class);
        when(mockedClass.getOldTarget()).thenReturn(oldTarget);
        EntityRef newTarget = mock(EntityRef.class);
        when(mockedClass.getNewTarget()).thenReturn(newTarget);

        assertEquals(true, e.copyEvent(mockedClass) instanceof CameraTargetChangedEvent);

        CameraTargetChangedEvent newEvent = (CameraTargetChangedEvent) e.copyEvent(mockedClass);
        assertEquals(mockedClass.getOldTarget(), newEvent.getOldTarget());
        assertEquals(mockedClass.getNewTarget(), newEvent.getNewTarget());
    }

    @Test
    public void copyCharacterMoveInputEventTest() {
        CharacterMoveInputEvent mockedClass = mock(CharacterMoveInputEvent.class);

        long delta = 1l;
        when(mockedClass.getDeltaMs()).thenReturn(delta);
        float pitch = 1f;
        when(mockedClass.getPitch()).thenReturn(pitch);
        float yaw = 1f;
        when(mockedClass.getYaw()).thenReturn(yaw);
        Vector3f movementDirection = new Vector3f(1,1,1);
        when(mockedClass.getMovementDirection()).thenReturn(movementDirection);
        Boolean crouching = true;
        when(mockedClass.isCrouching()).thenReturn(crouching);
        Boolean running = true;
        when(mockedClass.isRunning()).thenReturn(running);
        Boolean jumpRequested = true;
        when(mockedClass.isJumpRequested()).thenReturn(jumpRequested);
        int sequenceNumber = 1;
        when(mockedClass.getSequenceNumber()).thenReturn(sequenceNumber);

        assertEquals(true, e.copyEvent(mockedClass) instanceof CharacterMoveInputEvent);

        CharacterMoveInputEvent newEvent = (CharacterMoveInputEvent) e.copyEvent(mockedClass);
        assertEquals(mockedClass.getDeltaMs(), newEvent.getDeltaMs());
        assertEquals(mockedClass.getPitch(), newEvent.getPitch());
        assertEquals(mockedClass.getYaw(), newEvent.getYaw());
        assertEquals(mockedClass.getMovementDirection(), newEvent.getMovementDirection());
        assertEquals(mockedClass.isCrouching(), newEvent.isCrouching());
        assertEquals(mockedClass.isRunning(), newEvent.isRunning());
        assertEquals(mockedClass.isJumpRequested(), newEvent.isJumpRequested());
        assertEquals(mockedClass.getSequenceNumber(), newEvent.getSequenceNumber());
    }

    @Test
    public void copyMouseButtonEventTest() {
        MouseButtonEvent mockedClass = mock(MouseButtonEvent.class);

        Vector2i mouseMousePosition = new Vector2i(1,1);
        when(mockedClass.getMousePosition()).thenReturn(mouseMousePosition);
        MouseInput button = MouseInput.MOUSE_RIGHT;
        when(mockedClass.getButton()).thenReturn(button);
        ButtonState state = ButtonState.DOWN;
        when(mockedClass.getState()).thenReturn(state);

        assertEquals(true, e.copyEvent(mockedClass) instanceof MouseButtonEvent);

        MouseButtonEvent newEvent = (MouseButtonEvent) e.copyEvent(mockedClass);
        assertEquals(mockedClass.getState(), newEvent.getState());
        assertEquals(mockedClass.getButton(), newEvent.getButton());
        assertEquals(mockedClass.getMousePosition(), newEvent.getMousePosition());
    }

    @Test
    public void copyMouseWheelEventTest() {
        MouseWheelEvent mockedClass = mock(MouseWheelEvent.class);

        Vector2i mouseMousePosition = new Vector2i(1,1);
        when(mockedClass.getMousePosition()).thenReturn(mouseMousePosition);
        int wheelTurns = 1;
        when(mockedClass.getWheelTurns()).thenReturn(wheelTurns);

        assertEquals(true, e.copyEvent(mockedClass) instanceof MouseWheelEvent);

        MouseWheelEvent newEvent = (MouseWheelEvent) e.copyEvent(mockedClass);
        assertEquals(mockedClass.getWheelTurns(), newEvent.getWheelTurns());
        assertEquals(mockedClass.getMousePosition(), newEvent.getMousePosition());
    }

    @Test
    public void copyGetMaxSpeedEvent() {
        GetMaxSpeedEvent mockedClass = mock(GetMaxSpeedEvent.class);

        MovementMode movementMode = MovementMode.WALKING;
        when(mockedClass.getMovementMode()).thenReturn(movementMode);

        assertEquals(true, e.copyEvent(mockedClass) instanceof GetMaxSpeedEvent);

        GetMaxSpeedEvent newEvent = (GetMaxSpeedEvent) e.copyEvent(mockedClass);
        assertEquals(mockedClass.getMovementMode(), newEvent.getMovementMode());
    }

    @Test
    public void copyAttackEventTest() {
        AttackEvent mockedClass = mock(AttackEvent.class);

        EntityRef instigator = mock(EntityRef.class);
        when(mockedClass.getInstigator()).thenReturn(instigator);
        EntityRef directCause = mock(EntityRef.class);
        when(mockedClass.getDirectCause()).thenReturn(directCause);

        assertEquals(true, e.copyEvent(mockedClass) instanceof AttackEvent);

        AttackEvent newEvent = (AttackEvent) e.copyEvent(mockedClass);
        assertEquals(mockedClass.getInstigator(), newEvent.getInstigator());
        assertEquals(mockedClass.getDirectCause(), newEvent.getDirectCause());
    }

    @Test
    public void copyNullEventTest() {
        Event mockedClass = mock(Event.class);

        assertEquals(null, e.copyEvent(mockedClass));
    }
}
