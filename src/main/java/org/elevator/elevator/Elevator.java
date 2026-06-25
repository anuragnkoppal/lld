package org.elevator.elevator;

import java.util.HashSet;
import java.util.Set;

import static org.elevator.elevator.Direction.DOWN;
import static org.elevator.elevator.Direction.UP;

public class Elevator {
    int floor;
    Direction direction;
    Set<ElevatorRequest> requestSet; // for concurrency ->     Set<ElevatorRequest> requestSet = ConcurrentHashMap.newKeySet();


    Elevator(){
        requestSet = new HashSet<>();
    }

    public boolean addRequests(ElevatorRequest req){
        requestSet.add(req);
        return true;
    }

    public void stop(){
        System.out.println("Elevator stopped");
    }

    public void step(){
        // basically we give direction to the elevator
        // if idle with requests, pick direction towards nearest
        // check if we should stop at the current floors (if request has current floor as destination)
        // if no requests ahead of us then reverse
        // Move one floor in the current direction

        // Edge cases
        // if no requests -> then idle
        // stop n move cant be together in same tick

        // case 1 : base case check
        if(requestSet.isEmpty()){
            direction = Direction.IDLE;
            return;
        }

        // case 2 : move idle elevators with requests
        if(direction.equals(Direction.IDLE)){
            ElevatorRequest nearestRequest = getNearestRequest(requestSet);
            direction = nearestRequest.floor > floor ? UP : DOWN;
        }

        // case 3 : check if requestSet has current floor as a stop
        RequestType type = direction == UP ? RequestType.PICKUP_UP : RequestType.PICKUP_DOWN;
        ElevatorRequest hallCallRequest = new ElevatorRequest(floor, type);
        ElevatorRequest destinationRequest = new ElevatorRequest(floor, RequestType.DESTINATION);

        if(requestSet.contains(hallCallRequest) || requestSet.contains(destinationRequest)){
            requestSet.remove(hallCallRequest);
            requestSet.remove(destinationRequest);
            stop();
            return;
        }

        // case 4 :  reverse if nothing ahead
        if(!hasRequestsAhead()){
            direction = direction == UP ? DOWN : UP;
            return;
        }

        // case 5 : move same direction
        if(direction == UP) floor++;
        else if(direction == DOWN) floor--;

    }

    int getFloor(){
        return floor;
    }

    Direction getDirection() {
        return direction;
    }

    private ElevatorRequest getNearestRequest(Set<ElevatorRequest> requests) {
        ElevatorRequest nearest = null;
        int minDistance = Integer.MAX_VALUE;

        for (ElevatorRequest request : requests) {
            int distance = Math.abs(request.floor - floor);
            if (distance < minDistance) {
                minDistance = distance;
                nearest = request;
            }
        }

        return nearest;
    }

    private boolean hasRequestsAhead() {
        for (ElevatorRequest request : requestSet) {
            if (direction == UP && request.floor > floor) {
                return true;
            }
            if (direction == DOWN && request.floor < floor) {
                return true;
            }
        }
        return false;
    }
}
