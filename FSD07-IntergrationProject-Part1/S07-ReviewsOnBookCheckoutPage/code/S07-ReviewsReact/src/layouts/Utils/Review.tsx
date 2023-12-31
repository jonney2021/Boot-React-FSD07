import ReviewModel from "../../models/ReviewModel";
import { StarsReview } from "./StarsReview";

export const Review: React.FC<{ review: ReviewModel }> = (props) => {
  // create a new date with the string that we're getting from the backend,
  // adjusting some of the data so we can create a string and present
  // the date how we want to present the date in the application.
  const date = new Date(props.review.date);
  const longMonth = date.toLocaleString("en-us", { month: "long" }); // TODO: Add localization support for displaying dates.
  const dateDay = date.getDate();
  const dateYear = date.getFullYear();
  const dateRender = longMonth + "" + dateDay + ", " + dateYear; // TODO: Consider using a date formatting library like 'moment.js' for better manageability.
  return (
    <div>
      <div className="col-sm-8 col-md-8">
        <h5>{props.review.userEmail}</h5> 
        {/* TODO: Consider anonymizing the email or showing username instead for privacy. */}
        <div className="row">
          <div className="col">{dateRender}</div>
          <div className="col">
            <StarsReview rating={props.review.rating} size={16} />
          </div>
        </div>
        <div className="mt-2">
          <p>{props.review.reviewDescription}</p>
          {/* TODO: Add support for rich text or markdown in review descriptions. */}
        </div>
      </div>
      <hr />
    </div>
  );
};
