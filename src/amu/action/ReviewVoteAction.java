package amu.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import amu.database.ReviewDAO;
import amu.model.Review;

public class ReviewVoteAction implements Action{

	@Override
	public ActionResponse execute(HttpServletRequest request,HttpServletResponse response) throws Exception {

		if(request.getParameter("action") != null){
			String action = request.getParameter("action");
			
			
			int reviewID = Integer.parseInt(request.getParameter("review"));
			ReviewDAO reviewDAO = new ReviewDAO();
			Review review = reviewDAO.findByReviewID(reviewID);
			
			System.out.println("clicked on vote");
			
			if(review != null){
				if(action.equals("+")){
					System.out.println("UPVOTED");
					review.setVotes(review.getVotes() + 1);
				}
				else if(action.equals("-")){
					System.out.println("DOWNVOTED");
					review.setVotes(review.getVotes() - 1);
				}
				
				reviewDAO.updateReview(review);
			}
		}
		else{
			System.out.println("action was null in ReviewVoteAction");
		}
		
		
		return new ActionResponse(ActionResponseType.FORWARD, "vote");
	}

}
