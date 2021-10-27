package ex0.algo;

import ex0.Building;
import ex0.Elevator;
import ex0.simulator.Simulator_A;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AlgoTest {
    Building b1;
    Building b9;
    Algo algo;

    public AlgoTest(){
        Simulator_A.initData(1,null);
        b1 = Simulator_A.getBuilding();
        Simulator_A.initData(9,null);
        b9 = Simulator_A.getBuilding();
        algo = new Algo(b1);
    }

    @Test
    void allocateAnElevator() {

    }

    @Test
    void insert() {

    }

    @Test
    void timeToFloor() {

    }

    @Test
    void cmdElevator() {

    }
}