package cga.flight;

import java.util.Date;

public class Period implements Comparable<Period>{
	
	Date start;
	Date end;
	public Period(Date s, Date e){
		start = s;
		end = e;
	}
	
	//length of period in minutes
	public static long length(Period p){
		return (p.end.getTime() - p.start.getTime())/60000; 
	}
	//check if 2 periods overlap each other
	public static boolean isOverLap(Period p1, Period p2){
		return (p1.start.after(p2.start)&&p1.start.before(p2.end))
				||(p1.end.after(p2.start)&&p1.end.before(p2.end))
				||(p1.start.equals(p2.start));
	}
	//length of overlap between 2 periods in minutes
	public static long overlapLength(Period p1, Period p2){
		if (p1.start.after(p2.start)&&p1.start.before(p2.end))
			return (p2.end.getTime()-p1.start.getTime())/60000;
		else if (p1.end.after(p2.start)&&p1.end.before(p2.end))
			return (p1.end.getTime()-p2.start.getTime())/60000;
		return 0;
	}
	@Override
	public String toString(){
		return "Start: "+start+" - End: "+end;
	}
	@Override
    public int compareTo(Period p){
        return this.start.compareTo(p.start);
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
