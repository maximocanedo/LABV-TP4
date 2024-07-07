package web.entity;

public enum Day {
	SUNDAY,
	MONDAY,
	TUESDAY,
	WEDNESDAY,
	THURSDAY,
	FRIDAY,
	SATURDAY;
	
	public Day next() {
        return values()[(this.ordinal() + 1) % values().length];
    }
}
