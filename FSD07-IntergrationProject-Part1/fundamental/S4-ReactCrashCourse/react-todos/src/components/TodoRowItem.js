function TodoRowItem(props) {
  return (
    <tr>
      <th scope="row">{props.rowNumber}</th>
      <td>{props.rowDescription}</td>
      <td>{props.rowAssigned}</td>
      <td>
        <button
          className="btn btn-danger"
          onClick={() => props.deleteTodo(props.rowNumber)}
        >
          Delete
        </button>
      </td>
    </tr>
  );
}

export default TodoRowItem;
