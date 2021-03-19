package networking;

public interface OnRequestCallback {
    public byte[] handleRequest(byte[] payload);

    public String getEndPoint();
}
