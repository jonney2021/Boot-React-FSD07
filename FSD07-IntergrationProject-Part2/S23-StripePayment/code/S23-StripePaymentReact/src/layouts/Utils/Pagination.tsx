export const Pagination: React.FC<{
  currentPage: number;
  totalPages: number;
  paginate: any;
}> = (props) => {
  const pageNumbers = [];

  // Logic for determining the displayed page numbers based on current page and total pages.
  // Consider refactoring this logic into a separate function for better readability and reusability.

  // Check if current page is the first page
  if (props.currentPage === 1) {
    pageNumbers.push(props.currentPage);
    if (props.totalPages >= props.currentPage + 1) {
      pageNumbers.push(props.currentPage + 1);
    }
    if (props.totalPages >= props.currentPage + 2) {
      pageNumbers.push(props.currentPage + 2);
    }
  } else if (props.currentPage > 1) {
    // Check if current page is greater than 1

    // Display previous page numbers
    if (props.currentPage >= 3) {
      pageNumbers.push(props.currentPage - 2);
      pageNumbers.push(props.currentPage - 1);
    } else {
      pageNumbers.push(props.currentPage - 1);
    }
    // Display current page and the next two pages if available
    pageNumbers.push(props.currentPage);
    if (props.totalPages >= props.currentPage + 1) {
      pageNumbers.push(props.currentPage + 1);
    }
    if (props.totalPages >= props.currentPage + 2) {
      pageNumbers.push(props.currentPage + 2);
    }
  }
  return (
    <nav aria-label="...">
      <ul className="pagination">
        {/* Render button for navigating to the first page */}
        <li className="page-item" onClick={() => props.paginate(1)}>
          <button className="page-link">First Page</button>
        </li>
        {/* Render page number buttons */}
        {pageNumbers.map((number) => (
          <li
            key={number}
            onClick={() => props.paginate(number)}
            className={
              "page-item" + (props.currentPage === number ? "active" : "")
            }
          >
            <button className="page-link">{number}</button>
          </li>
        ))}
        {/* Render button for navigating to the last page */}
        <li
          className="page-item"
          onClick={() => props.paginate(props.totalPages)}
        >
          <button className="page-link">Last Page</button>
        </li>
      </ul>
    </nav>
  );
};
