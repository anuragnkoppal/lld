package org.elevator.elevator;
import java.util.List;
import java.util.Objects;

public class ElevatorController {
    List<Elevator> elevators;

    boolean requestElevator(int floor, Direction direction) {
        ElevatorRequest request = new ElevatorRequest(floor, direction);
        Elevator elevator = selectBestElevator(request);
        return elevator.addRequests(request);
    }

    Elevator selectBestElevator(ElevatorRequest request){
        // try to find moving toward elevator
        // else find idle ones
        // else pick nearest

        if(Objects.isNull(request)){
            throw new IllegalArgumentException("Empty request");
        }

        Elevator resultingElevator = findMovingTowardElevator(request);
        if(Objects.nonNull(resultingElevator)) return resultingElevator;

        resultingElevator = findIdleElevator(request);
        if(Objects.nonNull(resultingElevator)) return resultingElevator;

        resultingElevator = findNearestElevator(request);
        if(Objects.nonNull(resultingElevator)) return resultingElevator;

        return null;
    }

    Elevator findMovingTowardElevator(ElevatorRequest request){
        if(Objects.isNull(request)){
            throw new IllegalArgumentException("Empty request");
        }

        Elevator nearest = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator elevator : this.elevators){

            if(!elevator.direction.equals(request.direction)) continue;;

            if(elevator.direction == Direction.UP &&  elevator.floor > request.floor || elevator.direction == Direction.DOWN && elevator.floor < request.floor) continue;

            int distance = Math.abs(elevator.floor - request.floor);
            if(distance < minDistance){
                nearest = elevator;
                minDistance = distance;
            }
        }

        return nearest;
    }

    Elevator findIdleElevator(ElevatorRequest request) {
        for (Elevator elevator : elevators) {
            if (elevator.direction == Direction.IDLE) {
                return elevator;
            }
        }
        return null;
    }

    Elevator findNearestElevator(ElevatorRequest request){
        Elevator nearest = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {
            int distance = Math.abs(elevator.floor - request.floor);
            if (distance < minDistance) {
                nearest = elevator;
                minDistance = distance;
            }
        }

        return nearest;
    }

    void step(){
        for (Elevator elevator : elevators) {
            elevator.step();
        }
    }

    ElevatorController(List<Elevator> elevators){
        this.elevators  = elevators;
    }
}
