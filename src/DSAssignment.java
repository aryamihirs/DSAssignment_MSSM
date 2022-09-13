import java.io.*;
import java.util.*;

public class DSAssignment {
    public static void main(String[] args)
    {
        File inFile = null;
        HashMap<Integer, List<Integer>> allGuards = new HashMap<>();
        HashMap<Integer, Integer> startTimes = new HashMap<>();
        HashMap<Integer, Integer> endTimes = new HashMap<>();
        List<Integer> timeLine = new ArrayList<>();
        Set<List<Integer>> S = new HashSet<>(Collections.emptySet());
        int N = 0;


        if (0 < args.length) {
            inFile = new File(args[0]);
        } else {
            System.err.println("Invalid arguments count:" + args.length);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(inFile)))
        {
            String line;
            int i=0;
            List<Integer> time = new ArrayList<>();
            while ((line = br.readLine()) != null) {
//                System.out.println(line);
                if(i == 0){
                    N = Integer.parseInt(line);
                }
                else{
                    String[] times = line.split(" ");
                    int inTime = Integer.parseInt(times[0]);
                    int endTime = Integer.parseInt(times[1]);
                    List<Integer> timeInt =new ArrayList<Integer>(){{
                        add(inTime);
                        add(endTime);
                    }};
                    allGuards.put(i, timeInt);
                    startTimes.put(inTime, i);
                    endTimes.put(endTime, i);
                    timeLine.add(inTime);
                    timeLine.add(endTime);
                }
                i++;
            }
//            System.out.println(N);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        Collections.sort(timeLine);
        int minOverlapTime = Integer.MAX_VALUE;
        int sum = 0;
        int emptyIntervalSum = 0;
//        System.out.println("startTime : " + startTimes);
//        System.out.println("endTimes : " + endTimes);
//        System.out.println("allGuards : " + allGuards);

        for(int i = 0; i < timeLine.size() ; i++){
            System.out.println("timeLine element: " + timeLine.get(i));
            System.out.println(S);
            if(i > 0 && S.isEmpty()){
                System.out.println("I am here");
                emptyIntervalSum += (timeLine.get(i) - timeLine.get(i - 1));
            }
            if(S.stream().count() == 1){
                System.out.println("i : " + i);
                int startTime = S.stream().findAny().get().get(0);
                System.out.println("start time for Set element : " + startTime);
                minOverlapTime = Math.min(minOverlapTime, timeLine.get(i) - timeLine.get(i-1));
                System.out.println("minOverlapTime : " + minOverlapTime);
            }
            if(startTimes.containsKey(timeLine.get(i))){
                S.add(allGuards.get(startTimes.get(timeLine.get(i))));
            }
            if (endTimes.containsKey(timeLine.get(i)))
            {
                S.remove(allGuards.get(endTimes.get(timeLine.get(i))));
            }
//            else if (startTimes.containsKey(timeLine.get(i)) && endTimes.containsKey(timeLine.get(i))) {
//                S.add(allGuards.get(startTimes.get(timeLine.get(i))));
//                S.remove(allGuards.get(endTimes.get(timeLine.get(i))));
//            }

            if(i > 0) {
                sum += timeLine.get(i) - timeLine.get(i - 1);
            }
        }
//        int maxShiftCovered = sum - minOverlapTime;
        int totalTimeCovered = timeLine.get(2*N - 1) - timeLine.get(0);
        int shiftCovered = sum - emptyIntervalSum;
        int maxShiftCovered = shiftCovered - minOverlapTime;
        System.out.println("sum is : " + sum);
        System.out.println("minOverlapTime is : " + minOverlapTime);
        System.out.println("max possible shift time : " + maxShiftCovered);

        String outFile = inFile.getName();
        String outFileName = outFile.replaceFirst("[.][^.]+$", "");

        try {
            FileWriter myWriter = new FileWriter(outFileName + ".out");
            myWriter.write(String.valueOf(maxShiftCovered));
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}

