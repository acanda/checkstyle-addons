public class ChopDownIndent {

    // not ok, p2 should be at same column as p1
    public ChopDownIndent(final int p1,
                           final int p2) { }

    // ok
    public void f1() { }

    // ok
    public void f2(final int p1) { }

    // ok, all on the same line
    public void f3(final int p1, final int p2) { }

    // ok, correctly indented
    public void f3(final int p1,
                   final int p2) { }
    
    // ok, correctly indented
    public void f4(
        final int p1,
        final int p2
    ) { }

    // not ok, p2 should be at same column as p1
    public void f5(final int p1,
                    final int p2) { }

    // ok, correctly indented, with tabs
	public void f6(
		final int p1,
		final int p2
	) { }

    // not ok, p2 should be at same column as p1, with tabs
	public void f7(
		final int p1,
			final int p2
	) { }

}
