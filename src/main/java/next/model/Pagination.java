package next.model;

public class Pagination<T> {
	private T content;
	private long totalCount;
	private long currentPage;
	private boolean isFirst;
	private boolean isLast;

	public Pagination(T content, long totalCount, long currentPage, boolean isFirst, boolean isLast) {
		this.content = content;
		this.totalCount = totalCount;
		this.currentPage = currentPage;
		this.isFirst = isFirst;
		this.isLast = isLast;
	}

	public T getContent() {
		return content;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public boolean isFirst() {
		return isFirst;
	}

	public boolean isLast() {
		return isLast;
	}
}
