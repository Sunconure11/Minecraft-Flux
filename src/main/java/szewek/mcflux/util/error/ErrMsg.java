package szewek.mcflux.util.error;

public abstract class ErrMsg {
	private static final long ERRORS_TIME = 2000;
	protected final String name;
	protected final Class<?> cl;
	protected final Throwable msgThrown;
	private int count, lastCount;
	private long nextShow;

	protected ErrMsg(String name, Class<?> cl, Throwable thrown) {
		this.name = name;
		this.cl = cl;
		msgThrown = thrown;
		count = 0;
	}

	@Override public int hashCode() {
		return (this.getClass().hashCode() << 24) + (cl.hashCode() << 16) + name.hashCode();
	}

	@Override public boolean equals(Object obj) {
		return obj == this || obj instanceof ErrMsg && obj.hashCode() == hashCode();
	}

	public void addUp() {
		long now = System.currentTimeMillis();
		if (count == 0) {
			count = 1;
			printError();
			nextShow = now + ERRORS_TIME;
			lastCount = 1;
			return;
		}
		count++;
		if (nextShow < now) {
			printShortError(count - lastCount, now - nextShow - ERRORS_TIME);
			nextShow = now + ERRORS_TIME;
			lastCount = count;
		}
	}

	protected abstract void printError();
	protected abstract void printShortError(int total, long delta);
}