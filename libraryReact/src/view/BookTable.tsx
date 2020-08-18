import React, {Dispatch} from 'react';
import MaterialTable, {Column} from 'material-table';
import {Book} from "../model/Book";
import {useDispatch, useSelector} from "react-redux";
import {AppState} from "../store/Storable";
import CircularIndeterminate from "./Loader";
import {loadGenres, loadAuthors, loadBooks, addBook, updateBook, deleteBook, loadComments} from "../store/Actions";
import Typography from "@material-ui/core/Typography";
import {Container} from "@material-ui/core";
import AddCommentDialog from "./AddCommentDialog";

export interface BookRow {
    name: string;
    author: string;
    genre: string;
    year: number;
    book: Book;
}

class SelectedBookState {
    static readonly nonSelectedBook = new SelectedBookState(Book.empty)

    constructor(readonly book : Book) {
    }
}

interface Lookup {
    [id : string] : string
}

export function BookTable() {
    const isLoadedBooks = useSelector((state: AppState) => state.isLoadedBooks)
    const isLoadedAuthors = useSelector((state: AppState) => state.isLoadedAuthors)
    const isLoadedGenres = useSelector((state: AppState) => state.isLoadedGenres)
    const books = useSelector((state: AppState) => {return state.books})
    const genres = useSelector((state: AppState) => {return state.genres})
    const authors = useSelector((state: AppState) => {return state.authors})
    const dispatch = useDispatch()
    const [selectedBookState , setStateSelectedBook] = React.useState(SelectedBookState.nonSelectedBook)

    if (!isLoadedGenres){
        loadGenres(dispatch)
        return (
            <CircularIndeterminate/>
        )
    }
    if (!isLoadedAuthors){
        loadAuthors(dispatch)
        return (
            <CircularIndeterminate/>
        )
    }
    if (!isLoadedBooks){
        loadBooks(dispatch)
        return (
            <CircularIndeterminate/>
        )
    }

    const bookRows: BookRow[] = books.map(book => {
        return {
            name: book.name,
            author: book.author.id,
            genre: book.genre.id,
            year: book.year,
            book: book
        }
    })

    const genresLookup : Lookup = {}
    genres.forEach( genre => {
        genresLookup[genre.id] = genre.name
    })

    const authorsLookup: Lookup = {}
    authors.forEach( author => {
        authorsLookup[author.id] = author.name + " " + author.surname
    })

    const columns : Column<BookRow>[] = [
        {title: 'Book name', field: 'name'},
        {title: 'Author name', field: 'author', lookup: authorsLookup},
        {title: 'Genre name', field: 'genre', lookup: genresLookup},
        {title: 'Year', field: 'year', type:'numeric'}
    ]

    return (
        <>
            <MaterialTable
                title="Books"
                columns={columns}
                data={bookRows}
                options={{
                    actionsColumnIndex: -1
                }}
                editable={{
                    onRowAdd: (newData) =>{
                        addBook(dispatch, newData)
                        return Promise.resolve()
                    },
                    onRowUpdate: (newData, oldData) =>{
                        updateBook(dispatch, newData)
                        return Promise.resolve()
                    },
                    onRowDelete: (oldData) =>{
                        deleteBook(dispatch, oldData.book.id)
                        return Promise.resolve()
                    }
                }}
                onRowClick={(event, rowData) => {
                    if (rowData != undefined) {
                        if (!rowData.book.isLoadedComments) {
                            loadComments(dispatch, rowData.book.id)
                        }
                        setStateSelectedBook(new SelectedBookState(rowData.book))
                    }
                }}
            />
            <AuthorDetailsPanel book={selectedBookState.book} dispatch = {dispatch}/>
        </>
    );
}

interface BookDetailsProps {
    book: Book
    dispatch: Dispatch<any>
}

function AuthorDetailsPanel(props: BookDetailsProps) {
    const book = props.book

    const listItems = book.comments.map(comment => <li><Typography color="textPrimary" align="left">{comment}</Typography></li>)
    const size = book.comments.length

    const addCommentHandler = () => {

    }

    if (props.book == Book.empty) {
        return <></>
    } else {
        return (<Container maxWidth="sm">
            <p></p>
            <div>
                <Typography variant="h4" color="textPrimary">{book.name}</Typography>
            </div>
            <div>
                <Typography variant="h4" color="textPrimary">{book.author.name} {book.author.surname}</Typography>
            </div>
            <div>
                <Typography variant="h6" color="textPrimary" align="left">Comments: </Typography>
            </div>
            <ul>
                {listItems}
            </ul>
            <AddCommentDialog book={book} dispatch={props.dispatch}/>
        </Container>)
    }
}