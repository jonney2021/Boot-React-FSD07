import { useEffect, useState } from "react";
import BookModel from "../../../models/BookModel";
import { SpinnerLoading } from "../../Utils/SpinnerLoading";
import { Pagination } from "../../Utils/Pagination";
import { ChangeQuantityOfBook } from "./ChangeQuantityOfBook";

export const ChangeQuantityOfBooks = () => {
  // Create a book array
  const [books, setBooks] = useState<BookModel[]>([]);
  // For judging whether in loading
  const [isLoading, setIsLoading] = useState(true);
  // API call failure scenario
  const [httpError, setHttpError] = useState(null);

  // pagination array
  const [currentPage, setCurrentPage] = useState(1);
  // each page show 5 books
  const [booksPerPage] = useState(5);
  // total amounts of books
  const [totalAmountOfBooks, setTotalAmountOfBooks] = useState(0);
  // total pages
  const [totalPages, setTotalPages] = useState(0);

  // delete book useState
  const [bookDelete, setBookDelete] = useState(false);

  useEffect(() => {
    const fetchBooks = async () => {
      const baseUrl: string = `${process.env.REACT_APP_API}/books?page=${
        currentPage - 1
      }&size=${booksPerPage}`;

      // fetching the url data
      const response = await fetch(baseUrl);
      //failure scenario
      if (!response.ok) {
        throw new Error("Something went wrong!");
      }
      // transfer the url data into json;
      const responseJson = await response.json();
      // get the data which is the object of embedded books
      const responseData = responseJson._embedded.books;
      //get the total amount of books
      setTotalAmountOfBooks(responseJson.page.totalElements);
      //get the total pages to show these books
      setTotalPages(responseJson.page.totalPages);
      // create a book array
      const loadedBooks: BookModel[] = [];
      // iterate the object in responseData, push them into the book array,and set the loading process finished.
      for (const key in responseData) {
        loadedBooks.push({
          id: responseData[key].id,
          title: responseData[key].title,
          author: responseData[key].author,
          description: responseData[key].description,
          copies: responseData[key].copies,
          copiesAvailable: responseData[key].copiesAvailable,
          category: responseData[key].category,
          img: responseData[key].img,
        });
      }
      setBooks(loadedBooks);
      setIsLoading(false);
    };
    // if async has an error
    fetchBooks().catch((error: any) => {
      setIsLoading(false);
      setHttpError(error.message);
    });
    // pass current page , so each time we click on different page number, the data will be refreshed.
  }, [currentPage, bookDelete]);

  // pagination functionality
  const indexOfLastBook: number = currentPage * booksPerPage;
  const indexOfFirstBook: number = indexOfLastBook - booksPerPage;
  let lastItem =
    booksPerPage * currentPage <= totalAmountOfBooks
      ? booksPerPage * currentPage
      : totalAmountOfBooks;

  const paginate = (pageNumber: number) => setCurrentPage(pageNumber);
  const deleteBook = () => setBookDelete(!bookDelete);

  // when in loading process, show: "Loading..."
  if (isLoading) {
    return <SpinnerLoading />;
  }

  // if there is error in fetch data
  if (httpError) {
    return (
      <div className="container m-5">
        <p>{httpError}</p>
      </div>
    );
  }

  return (
    <div className="container mt-5">
      {/* judge if the total amount of books is positive, which means the book exists */}
      {totalAmountOfBooks > 0 ? (
        <>
          <div className="mt-3">
            <h3>Number of results: ({totalAmountOfBooks})</h3>
          </div>
          <p>
            {indexOfFirstBook + 1} to {lastItem} of {totalAmountOfBooks} items:
          </p>
          {books.map((book) => (
            <ChangeQuantityOfBook
              book={book}
              key={book.id}
              deleteBook={deleteBook}
            />
          ))}
        </>
      ) : (
        // if no book
        <h5>Add a book before changing quantity</h5>
      )}
      {totalPages > 1 && (
        <Pagination
          currentPage={currentPage}
          totalPages={totalPages}
          paginate={paginate}
        />
      )}
    </div>
  );
};
