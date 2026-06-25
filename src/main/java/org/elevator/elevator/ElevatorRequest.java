package org.elevator.elevator;

import java.util.Objects;

public class ElevatorRequest {
    int floor;
    Direction direction;

    ElevatorRequest(int floor, Direction direction){
        this.floor = floor;
        this.direction = direction;
    }

    ElevatorRequest(int floor, RequestType type){
        this.floor = floor;
        this.direction = type.equals(RequestType.PICKUP_UP) ? Direction.UP : Direction.DOWN;
    }

    int getFloor(){
        return floor;
    }

    Direction getDirection(){
        return direction;
    }

    // needed for requestSet.contains(...) in Elevator.step()
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ElevatorRequest that)) {
            return false;
        }
        return floor == that.floor && direction == that.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(floor, direction);
    }
}
