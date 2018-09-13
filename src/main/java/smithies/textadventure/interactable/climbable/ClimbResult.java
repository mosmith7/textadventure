package smithies.textadventure.interactable.climbable;

public class ClimbResult {

    private boolean success;
    private String resultMessage;

    public ClimbResult(boolean success, String resultMessage) {
        this.success = success;
        this.resultMessage = resultMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getResultMessage() {
        return resultMessage;
    }
}
