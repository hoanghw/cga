package cga.algorithm;

import java.util.ArrayList;

public class Statistics 
{
    ArrayList<Long> data = new ArrayList<Long>();
    long size;    

    public Statistics(ArrayList<Long> data) 
    {
        this.data = data;
        size = data.size();
    }   

    long getMean()
    {
        long sum = 0;
        for(long a : data)
            sum += a;
            return sum/size;
    }

	long getVariance()
	{
		long mean = getMean();
		long temp = 0;
		for(long a : data)
		temp += (mean-a)*(mean-a);
		return temp/size;
	}

    long getStdDev()
	{
		return (long) Math.sqrt(getVariance());
	}
}
