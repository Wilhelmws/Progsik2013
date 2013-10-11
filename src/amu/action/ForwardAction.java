package amu.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class ForwardAction implements Action {

    private final ActionResponse actionResponse;

    public ForwardAction(String url) {
        this.actionResponse = new ActionResponse(ActionResponseType.FORWARD, url);
    }

    public ForwardAction(ActionResponse actionResponse) {
        this.actionResponse = actionResponse;
    }

    @Override
    public ActionResponse execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return actionResponse;
    }
}
