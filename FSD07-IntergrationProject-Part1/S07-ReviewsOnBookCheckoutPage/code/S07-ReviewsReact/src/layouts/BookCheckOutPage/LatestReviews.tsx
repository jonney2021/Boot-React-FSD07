import { Link } from "react-router-dom";
import ReviewModel from "../../models/ReviewModel";
import { Review } from "../Utils/Review";

export const LatestReviews: React.FC<{
  reviews: ReviewModel[];
  bookId: number | undefined;
  mobile: boolean;
}> = (props) => {
  return (
    <div className={props.mobile ? "mt-3" : "row mt-5"}>
      {/* TODO: Refactor to use responsive CSS classes instead of JavaScript logic */}
      <div className={props.mobile ? "" : "col-sm-2 col-md-2"}>
        <h2>Latest Reviews:</h2>
      </div>
      <div className="col-sm-10 col-md-10">
        {props.reviews.length > 0 ? (
          <>
            {props.reviews.slice(0, 3).map((eachReview) => (
            // TODO: Implement pagination or a "See More" feature
              <Review review={eachReview} key={eachReview.id}></Review>
            ))}
            <div className="m-3">
              <Link
                type="button"
                className="btn main-color btn-md text-white"
                to="#"
              >
                {/* TODO: Implement link to the 'All Reviews' page. */}
                Reach all reviews
              </Link>
            </div>
          </>
        ) : (
          <div className="m-3">
            <p className="lead">
              Currently there are no reviews for this book.
              {/* TODO: Enhance the 'No Reviews' message display. */}
            </p>
          </div>
        )}
      </div>
    </div>
  );
};
