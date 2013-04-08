package cga.flight;

import java.util.Date;

public class Period implements Comparable<Period>{
	
	Date start;
	Date end;
	public Period(Date s, Date e){
		start = s;
		end = e;
	}
	public static boolean isOverLap(Period p1, Period p2){
		return (p1.start.after(p2.start)&&p1.start.before(p2.end))
				||(p1.end.after(p2.start)&&p1.end.before(p2.end));
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
