package org.elevator.elevator;

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
}
