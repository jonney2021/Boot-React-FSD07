import { useEffect, useState } from "react";
import BookModel from "../../models/BookModel";
import ReviewModel from "../../models/ReviewModel";
import { SpinnerLoading } from "../Utils/SpinnerLoading";
import { StarsReview } from "../Utils/StarsReview";
import { CheckoutAndReviewBox } from "./CheckOutAndReviewBox";
import { LatestReviews } from "./LatestReviews";
import { useOktaAuth } from "@okta/okta-react";
import ReviewRequestModel from "../../models/ReviewRequestModel";

export const BookCheckOutPage = () => {
  const { authState } = useOktaAuth();

  // create useState
  const [book, setBook] = useState<BookModel>();
  const [isLoading, setIsLoading] = useState(true);
  const [httpError, setHttpError] = useState(null);

  //Review State
  const [reviews, setReviews] = useState<ReviewModel[]>([]);
  const [totalStars, setTotalStars] = useState(0);
  const [isLoadingReview, setIsLoadingReview] = useState(true);

  const [isReviewLeft, setIsReviewLeft] = useState(false);
  const [isLoadingUserReview, setIsLoadingUserReview] = useState(true);

  // Loans Count State
  const [currentLoansCount, setCurrentLoansCount] = useState(0);
  const [isLoadingCurrentLoansCount, setIsLoadingCurrentLoansCount] =
    useState(true);

  // Is Book Check out?
  const [isCheckedOut, setIsCheckedOut] = useState(false);
  const [isLoadingBookCheckedOut, setIsLoadingBookCheckedOut] = useState(true);

  const bookId = window.location.pathname.split("/")[2];

  // fetch book by ID useEffect
  useEffect(() => {
    const fetchBook = async () => {
      const baseUrl: string = `http://localhost:8080/api/books/${bookId}`;
      // fetching the url data
      const response = await fetch(baseUrl);
      //failure scenario
      if (!response.ok) {
        throw new Error("Something went wrong!");
      }
      // transfer the url data into json;
      const responseJson = await response.json();

      // create a book array
      const loadedBook: BookModel = {
        id: responseJson.id,
        title: responseJson.title,
        author: responseJson.author,
        description: responseJson.description,
        copies: responseJson.copies,
        copiesAvailable: responseJson.copiesAvailable,
        category: responseJson.category,
        img: responseJson.img,
      };

      setBook(loadedBook);
      setIsLoading(false);
    };
    fetchBook().catch((error: any) => {
      setIsLoading(false);
      setHttpError(error.message);
    });
  }, [isCheckedOut]);

  //create review useEffect
  useEffect(() => {
    const fetchBookReviews = async () => {
      const reviewUrl: string = `http://localhost:8080/api/reviews/search/findByBookId?bookId=${bookId}`;
      const responseReviews = await fetch(reviewUrl);
      if (!responseReviews.ok) {
        throw new Error("Something went wrong!");
      }
      const responseJsonReviews = await responseReviews.json();
      const responseData = responseJsonReviews._embedded.reviews;
      const loadedReviews: ReviewModel[] = [];
      let weightedStarReviews: number = 0;
      for (const key in responseData) {
        loadedReviews.push({
          id: responseData[key].id,
          userEmail: responseData[key].userEmail,
          date: responseData[key].date,
          rating: responseData[key].rating,
          book_id: responseData[key].bookId,
          reviewDescription: responseData[key].reviewDescription,
        });
        weightedStarReviews = weightedStarReviews + responseData[key].rating;
      }

      if (loadedReviews) {
        const round = (
          Math.round((weightedStarReviews / loadedReviews.length) * 2) / 2
        ).toFixed(1);
        setTotalStars(Number(round));
      }

      setReviews(loadedReviews);
      setIsLoadingReview(false);
    };
    fetchBookReviews().catch((error: any) => {
      setIsLoadingReview(false);
      setHttpError(error.message);
    });
  }, [isReviewLeft]);

  // get a single review useEffect
  useEffect(() => {
    const fetchUserReviewBook = async () => {
      //call api with the url and method type, headers
      if (authState && authState.isAuthenticated) {
        const url = `http://localhost:8080/api/reviews/secure/user/book/?bookId=${bookId}`;
        const requestOptions = {
          method: "GET",
          headers: {
            Authorization: `Bearer ${authState.accessToken?.accessToken}`,
            "Content-Type": "application/json",
          },
        };
        //fetch data with url and requestOptions
        const userReview = await fetch(url, requestOptions);
        //if failed
        if (!userReview.ok) {
          throw new Error("Somthing went wrong!");
        }
        // transfer to Json object
        const userReviewResponseJson = await userReview.json();
        setIsReviewLeft(userReviewResponseJson);
      }
      setIsLoadingUserReview(false);
    };
    fetchUserReviewBook().catch((error: any) => {
      setIsLoadingUserReview(false);
      setHttpError(error.message);
    });
  }, [authState]);

  // loans count useEffect
  useEffect(() => {
    const fetchUserCurrentLoansCount = async () => {
      // make sure user is autenticated
      if (authState && authState.isAuthenticated) {
        // endpoint we need to call
        const url = `http://localhost:8080/api/books/secure/currentloans/count`;
        // need to specify the method type and headers
        const requestOptions = {
          method: "GET",
          headers: {
            Authorization: `Bearer ${authState.accessToken?.accessToken}`,
            "Content-Type": "application/json",
          },
        };
        // fetch the data with the url and our method , headers
        const currentLoansCountResponse = await fetch(url, requestOptions);
        // if there is a failure of call api
        if (!currentLoansCountResponse.ok) {
          throw new Error("Something went wrong!");
        }
        // if fetching data is successful, transfer the data into json
        const currentLoansCountReponseJson =
          await currentLoansCountResponse.json();
        //save the response into our current loans count
        setCurrentLoansCount(currentLoansCountReponseJson);
      }
      // set the loading process finished
      setIsLoadingCurrentLoansCount(false);
    };
    fetchUserCurrentLoansCount().catch((error: any) => {
      setIsLoadingCurrentLoansCount(false);
      setHttpError(error.message);
    });
  }, [authState, isCheckedOut]);

  useEffect(() => {
    const fetchUserCheckedOutBook = async () => {
      if (authState && authState.isAuthenticated) {
        const url = `http://localhost:8080/api/books/secure/ischeckedout/byuser/?bookId=${bookId}`;
        const requestOptions = {
          method: "GET",
          headers: {
            Authorization: `Bearer ${authState.accessToken?.accessToken}`,
            "Content-Type": "application/json",
          },
        };
        const bookCheckedOut = await fetch(url, requestOptions);

        if (!bookCheckedOut.ok) {
          throw new Error("Something went wrong!");
        }

        const bookCheckedOutReponseJson = await bookCheckedOut.json();
        setIsCheckedOut(bookCheckedOutReponseJson);
      }
      setIsLoadingBookCheckedOut(false);
    };
    fetchUserCheckedOutBook().catch((error: any) => {
      setIsLoadingBookCheckedOut(false);
      setHttpError(error.message);
    });
  }, [authState]);

  if (
    isLoading ||
    isLoadingReview ||
    isLoadingCurrentLoansCount ||
    isLoadingBookCheckedOut ||
    isLoadingUserReview
  ) {
    return <SpinnerLoading />;
  }

  if (httpError) {
    return (
      <div className="container m-5">
        <p>{httpError}</p>
      </div>
    );
  }

  async function checkoutBook() {
    // call the api
    const url = `http://localhost:8080/api/books/secure/checkout/?bookId=${book?.id}`;
    // specify the method type and th headers
    const requestOptions = {
      method: "PUT",
      headers: {
        Authorization: `Bearer ${authState?.accessToken?.accessToken}`,
        "Content-Type": "application/json",
      },
    };
    // fetch the data
    const checkoutResponse = await fetch(url, requestOptions);
    if (!checkoutResponse.ok) {
      throw new Error("Something went wrong");
    }
    setIsCheckedOut(true);
  }

  // submit review
  async function submitReview(starInput: number, reviewDescription: string) {
    let bookId: number = 0;
    if (book?.id) {
      //grab the bookId
      bookId = book.id;
    }

    //create a new review request based on the star input , bookId and description
    const reviewRequestModel = new ReviewRequestModel(
      starInput,
      bookId,
      reviewDescription
    );
    // url to access to our spring boot application
    const url = `http://localhost:8080/api/reviews/secure`;
    // method type and headers
    const requestOptions = {
      method: "POST",
      headers: {
        Authorization: `Bearer ${authState?.accessToken?.accessToken}`,
        "Content-Type": "application/json",
      },
      //body for post method
      body: JSON.stringify(reviewRequestModel),
    };
    //call the APi to fetch the data
    const returnResponse = await fetch(url, requestOptions);
    if (!returnResponse.ok) {
      throw new Error("Something went wrong!");
    }
    setIsReviewLeft(true);
  }

  return (
    <div>
      <div className="container d-none d-lg-block">
        <div className="row mt-5">
          {/* left side of the page: book image part1 */}
          <div className="col-sm-2 col-md-2">
            {book?.img ? (
              <img src={book?.img} width="226" height="349" alt="Book" />
            ) : (
              <img
                src={require("./../../Images/BooksImages/book-luv2code-1000.png")}
                width="226"
                height="349"
                alt="Book"
              />
            )}
          </div>
          {/* right side of the page: book info part */}
          <div className="col-4 col-md-4 container">
            <div className="ml-2">
              <h2>{book?.title}</h2>
              <h5 className="text-primary">{book?.author}</h5>
              <p className="lead">{book?.description}</p>
              {/* import StarsReview Component */}
              <StarsReview rating={totalStars} size={32} />
            </div>
          </div>
          <CheckoutAndReviewBox
            book={book}
            mobile={false}
            currentLoansCount={currentLoansCount}
            isAuthenticated={authState?.isAuthenticated}
            isCheckedOut={isCheckedOut}
            checkoutBook={checkoutBook}
            isReviewLeft={isReviewLeft}
            submitReview={submitReview}
          />
        </div>
        <hr />
        <LatestReviews reviews={reviews} bookId={book?.id} mobile={false} />
      </div>
      {/* mobile version */}
      {/* image part */}
      <div className="container d-lg-none mt-5">
        <div className="d-flex justify-content-center align-items-center">
          {book?.img ? (
            <img src={book?.img} width="226" height="349" alt="Book" />
          ) : (
            <img
              src={require("./../../Images/BooksImages/book-luv2code-1000.png")}
              width="226"
              height="349"
              alt="Book"
            />
          )}
        </div>
        {/* book info part */}
        <div className="mt-4">
          <div className="ml-2">
            <h2>{book?.title}</h2>
            <h5 className="text-primary">{book?.author}</h5>
            <p className="lead">{book?.description}</p>
            {/* import StarsReview Component */}
            <StarsReview rating={totalStars} size={32} />
          </div>
        </div>
        <CheckoutAndReviewBox
          book={book}
          mobile={true}
          currentLoansCount={currentLoansCount}
          isAuthenticated={authState?.isAuthenticated}
          isCheckedOut={isCheckedOut}
          checkoutBook={checkoutBook}
          isReviewLeft={isReviewLeft}
          submitReview={submitReview}
        />
        <hr />
        <LatestReviews reviews={reviews} bookId={book?.id} mobile={true} />
      </div>
    </div>
  );
};
