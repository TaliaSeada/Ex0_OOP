package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;
import ex0.simulator.Simulator_A;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlgoTest {
    Building b1; //[-2,10] elevators = 1
    Building b2; //[-2,10] elevators = 2
    Building b9; //[-10,100] elevators = 10
    Algo algo1;
    Algo algo2;
    Algo algo9;
    CallForElevator call;
    Elevator elev1;
    Elevator elev2;
    Elevator elev9;


    public AlgoTest() {
        Simulator_A.initData(1, null);
        b1 = Simulator_A.getBuilding();
        algo1 = new Algo(b1);
        elev1 = b1.getElevetor(0); //1

        Simulator_A.initData(2, null);
        b2 = Simulator_A.getBuilding();
        algo2 = new Algo(b2);
        elev2 = b1.getElevetor(0); //2

        Simulator_A.initData(9, null);
        b9 = Simulator_A.getBuilding();
        algo9 = new Algo(b9);
        elev9 = b9.getElevetor(0); //10

        callUp = new CallForElevator() {
            @Override
            public int getState() {
                return 0;
            }

            @Override
            public double getTime(int state) {
                return 0;
            }

            @Override
            public int getSrc() {
                return 35;
            }

            @Override
            public int getDest() {
                return 55;
            }

            @Override
            public int getType() {
                return CallForElevator.UP;
            }

            @Override
            public int allocatedTo() {
                return 0;
            }
        };

    }

    @Test
    void allocateAnElevator() {
        //This function gets a call and return the number of the elevator that was
        // allocated to take this call.

        //At the start of the process, all the elevators are empty
        //and elevator 3 is the fastest so this is why this is the expected outcome
        int elev1 = algo9.allocateAnElevator(callUp);
        assertEquals(3, elev1);

        int elev2 = algo9.allocateAnElevator(callDown);
        assertEquals(1, elev2);





    }

    @Test
    void insert() {
        /* this function was built in order to insert the calls more efficiently
          by checking if the elevator goes up or down and inserting the source floor between
          two consecutive floors then insert the destination floor after the source
          by the same way the source floor was added */

        MyQueue expected = new MyQueue(); //[0,-3,-4,-5,-1,0,19,40,70,19]
        expected.add(0);
        expected.add(-3);
        expected.add(-4);
        expected.add(-5);
        expected.add(-1);
        expected.add(0);
        expected.add(19);
        expected.add(40);
        expected.add(70);
        expected.add(19);
        /* In here we inserted the floors into the queue in the way they are supposed to
          be and then used our insert function to see if it inserts the floors correctly */
        int src1 = 0, src2 = -3, src3 = 70, src4 = 19, src5 = -1;
        int dest1 = -5, dest2 = -4, dest3 = 19, dest4 = 40, dest5 = 0;
        algo9.insert(src1, dest1, 0);
        algo9.insert(src2, dest2, 0);
        algo9.insert(src3, dest3, 0);
        algo9.insert(src4, dest4, 0);
        algo9.insert(src5, dest5, 0);

        //then compare between the results using the toString function we built
        assertEquals(expected.toString(), algo9.getCalls()[0].toString());

        //simple check if call is inserted to the end of the queue
        int src6 = 100;
        int dest6 = 90;
        expected.add(100);
        expected.add(90);
        algo9.insert(src6, dest6, 0); //[0,-3,-4,-5,-1,0,19,40,70,19,100,90]
        assertEquals(expected.toString(), algo9.getCalls()[0].toString());
    }

    @Test
    void timeToFloor() {
        /* This function get a floor and an elevator,and calculates the amount of time it will take the
          elevator to get to the floor, including all the floors that are currently in the
          elevator's queue */
        int src1 = 0, src2 = -1, src3 = 10, src4 = 6, src5 = -1;
        int dest1 = -2, dest2 = 4, dest3 = 3, dest4 = 9, dest5 = 0;
        algo2.insert(src1, dest1, 0);
        algo2.insert(src2, dest2, 0);
        algo2.insert(src3, dest3, 0);
        // =time = 22
        double time1 = algo2.timeToFloor(10, 0);
        assertEquals(time1, 22);

        int src6 = 0, src7 = -3, src8 = 70, src9 = 19, src10 = -1;
        int dest6 = -5, dest7 = -4, dest8 = 19, dest9 = 40, dest10 = 0;
        algo9.insert(src6, dest6, 0);
        algo9.insert(src7, dest7, 0);
        algo9.insert(src8, dest8, 0);
        algo9.insert(src9, dest9, 0);
        algo9.insert(src10, dest10, 0);
        double time2 = algo9.timeToFloor(96, 0);
        assertEquals(time2, 31.2);
    }

    @Test
    void cmdElevator() {

    }
}