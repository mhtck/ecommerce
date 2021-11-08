package core;

public class DataResult<T> extends Result{

	private T data;
	public DataResult(T data, boolean success, String message) {
		super(success);
		this.data = data;
	}
	
	public DataResult(T data, boolean success) {
		super(success);
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}