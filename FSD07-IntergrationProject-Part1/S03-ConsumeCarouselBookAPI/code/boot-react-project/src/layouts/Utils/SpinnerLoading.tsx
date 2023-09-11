export const SpinnerLoading = () => {
  // TODO: Add props for configurable size and color
  return (
    <div
      className="container m-5 d-flex justify-content-center"
      style={{ height: 550 }}
    >
      <div className="spinner-border text-primary" role="status">
        // TODO: Improve accessibility by providing more informative hidden text
        <span className="visually-hidden">Loading...</span>
      </div>
    </div>
  );
};
