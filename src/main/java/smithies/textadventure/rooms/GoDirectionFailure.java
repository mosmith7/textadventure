package smithies.textadventure.rooms;

public class GoDirectionFailure implements GoDirectionResponse {

    private String failureReason;

    public GoDirectionFailure(String reason) {
        this.failureReason = reason;
    }

    @Override
    public boolean isSuccessful() {
        return false;
    }

    public String getFailureReason() {
        return failureReason;
    }
}
