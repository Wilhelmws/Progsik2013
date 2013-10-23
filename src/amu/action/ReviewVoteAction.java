package amu.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import amu.database.ReviewDAO;
import amu.model.Review;

public class ReviewVoteAction implements Action{

	@Override
	public ActionResponse execute(HttpServletRequest request,HttpServletResponse response) throws Exception {

		if(request.getParameter("review") != null){

			int reviewID = Integer.parseInt(request.getParameter("review"));
			ReviewDAO reviewDAO = new ReviewDAO();
			Review review = reviewDAO.findByReviewID(reviewID);
			
			if(review != null){
				if(request.getParameter("up") != null){
					review.setVotes(review.getVotes() + 1);
				}
				else if(request.getParameter("down") != null){
					review.setVotes(review.getVotes() - 1);
				}
				
				reviewDAO.updateReview(review);
			}
		}
		else{
			System.out.println("ReviewVoteAction: review was: " + request.getParameter("action"));
		}
		
		return new ActionResponse(ActionResponseType.REDIRECT, "viewBook");
	}

}
