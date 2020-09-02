import {Genre} from "../model/Genre";
import {Author} from "../model/Author";
import {Book, BookDto} from "../model/Book";
import {
    ADD_AUTHOR,
    ADD_BOOK, ADD_COMMENT,
    ADD_GENRE, CLEAR_ERROR_MESSAGE,
    DELETE_AUTHOR,
    DELETE_BOOK,
    DELETE_GENRE, LOAD_AUTHORS, LOAD_BOOKS, LOAD_COMMENTS, LOAD_GENRES, SET_ERROR_MESSAGE,
    UPDATE_AUTHOR, UPDATE_BOOK,
    UPDATE_GENRE, SHOW_LOGIN_DIALOG, CLEAR_DATA
} from "./ActionConsts";
import {GenreRow} from "../view/GenreTable";
import {AuthorRow} from "../view/AuthorTable";
import {BookRow} from "../view/BookTable";
import {FetchProps} from "./Actions";

export interface AppState {
    genres: Array<Genre>
    authors: Array<Author>
    books: Array<Book>
    isLoadedGenres: boolean
    isLoadedAuthors: boolean
    isLoadedBooks: boolean
    errorMessage: string
    isShowLoginDialog: boolean
    fetchProps: FetchProps
}

const initialState: AppState = {
    genres: [],
    authors: [],
    books: [],
    isLoadedGenres: false,
    isLoadedAuthors: false,
    isLoadedBooks: false,
    errorMessage: "",
    isShowLoginDialog: false,
    fetchProps: {url: "", method:"", body:"", responseFunc: r => {} }
}

export function storable(state: AppState = initialState, action: any): AppState {

    switch (action.type) {
        case SHOW_LOGIN_DIALOG: {
            const isShowLogin = action.data as boolean
            const fetchProps = action.fetchProps as FetchProps
            return setShowLoginDialog(state, isShowLogin, fetchProps)
        }
        case CLEAR_DATA: {
            return clearData(state)
        }
        case LOAD_GENRES: {
            const genres = action.data as Array<Genre>
            return loadedGenres(state, genres);
        }
        case LOAD_AUTHORS: {
            const aurhors = action.data as Array<Author>
            return loadedAuthors(state, aurhors)
        }
        case LOAD_BOOKS: {
            const booksDto = action.data as Array<BookDto>
            return loadedBooks(state, booksDto)
        }
        case LOAD_COMMENTS: {
            const comments = action.comments as Array<string>
            const bookId = action.id
            return loadedComments(state, comments, bookId)
        }
        case ADD_GENRE: {
            const genre = action.data as Genre
            return addGenre(state, genre)
        }
        case UPDATE_GENRE: {
            const genreRow = action.row as GenreRow
            return updateGenre(state, genreRow.genre.id, genreRow.name)
        }
        case DELETE_GENRE: {
            const id = action.data as string
            return deleteGenre(state, id)
        }
        case ADD_AUTHOR: {
            const author = action.data as Author
            return addAuthor(state, author)
        }
        case UPDATE_AUTHOR: {
            const authorRow = action.row as AuthorRow
            return updateAuthor(state, authorRow.author.id, authorRow.name, authorRow.surname)
        }
        case DELETE_AUTHOR: {
            const id = action.data as string
            return deleteAuthor(state, id)
        }
        case ADD_BOOK: {
            const bookDto = action.data as BookDto
            return addBook(state, bookDto)
        }
        case UPDATE_BOOK: {
            const bookRow = action.row as BookRow
            return updateBook(state, bookRow)
        }
        case DELETE_BOOK: {
            const id = action.data as string
            return deleteBook(state, id)
        }
        case ADD_COMMENT: {
            const bookId = action.id as string
            const comment = action.comment as string
            return addComment(state, comment, bookId)
        }
        case SET_ERROR_MESSAGE: {
            const message = action.message
            return setErrorMessage(state, message)
        }
        case CLEAR_ERROR_MESSAGE: {
            return setErrorMessage(state, "")
        }
        default:
            return state
    }
}

function setShowLoginDialog(state: AppState, isShowLogin: boolean, fetchProps: FetchProps): AppState {
    let newFetchProps = state.fetchProps
    if (fetchProps != null){
        newFetchProps = fetchProps
    }
    return {...state, isShowLoginDialog: isShowLogin, fetchProps: newFetchProps}
}

function clearData(state: AppState): AppState {
    return {
        authors: [],
        genres: [],
        books: [],
        isLoadedGenres: false,
        isLoadedAuthors: false,
        isLoadedBooks: false,
        errorMessage: "",
        isShowLoginDialog: state.isShowLoginDialog,
        fetchProps: state.fetchProps
    }
}

function setErrorMessage(state: AppState, message: string): AppState {
    return {...state, errorMessage: message}
}

function loadedGenres(state: AppState, genres: Array<Genre>): AppState {
    return {...state, genres: genres, isLoadedGenres: true}
}

function loadedAuthors(state: AppState, authors: Array<Author>): AppState {
    return {...state, authors: authors, isLoadedAuthors: true}
}

function loadedBooks(state: AppState, booksDto: Array<BookDto>): AppState {
    if (state.isLoadedGenres === false || state.isLoadedAuthors === false){
        return state
    }
    const genres = state.genres
    const authors = state.authors
    const books: Array<Book> = []
    booksDto.forEach(bookDto => {
        const genreIndex = getIndex(genres, bookDto.genreId)
        const authorIndex = getIndex(authors, bookDto.authorId)
        if (genreIndex === -1 || authorIndex === -1)
            return
        const book = new Book(bookDto.id, bookDto.name, authors[authorIndex], genres[genreIndex], bookDto.year)
        books.push(book)
    })

    return {
        authors: state.authors,
        genres: state.genres,
        books: books,
        isLoadedGenres: state.isLoadedGenres,
        isLoadedAuthors: state.isLoadedAuthors,
        isLoadedBooks: true,
        errorMessage: "",
        isShowLoginDialog: state.isShowLoginDialog,
        fetchProps: state.fetchProps
    }
}

function loadedComments(state: AppState, comments: Array<string>, bookId: string): AppState{
    const books = state.books
    const bookIndex = getIndex(books, bookId)
    books[bookIndex].comments = comments
    books[bookIndex].isLoadedComments = true
    return {...state, books: [...books]}
}

function addComment(state: AppState, comment: string, bookId: string): AppState{
    const books = state.books
    const bookIndex = getIndex(books, bookId)
    books[bookIndex].addComment(comment)
    return {...state, books: [...books]}
}

function addGenre(state: AppState, genre: Genre): AppState {
    return {...state, genres: [...state.genres, genre]}
}

function updateGenre(state: AppState, id: string, name: string): AppState {
    const genres = state.genres
    const index = getIndex(genres, id)
    genres[index].name = name
    return {...state, genres: [...genres]}
}

function deleteGenre(state: AppState, id: string): AppState {
    const genres = state.genres
    const index = getIndex(genres, id)
    const newGenres = []
    for (let i = 0; i < genres.length; i++) {
        if (i != index) {
            newGenres.push(genres[i])
        }
    }

    const books = state.books
    const newBooks = []
    for (let i = 0; i< books.length; i++){
        if (books[i].genre.id != id){
            newBooks.push(books[i])
        }
    }
    return {...state, genres: newGenres, books: newBooks}
}

function addAuthor(state: AppState, author: Author): AppState {
    return {...state, authors: [...state.authors, author]}
}

function updateAuthor(state: AppState, id: string, name: string, surname: string): AppState {
    const authors = state.authors
    const index = getIndex(authors, id)
    authors[index].name = name
    authors[index].surname = surname
    return {...state, authors: [...authors]}
}

interface Identifier {
    id: string
}

function getIndex(arr: Array<Identifier>, id: string): number {
    for (let i = 0; i < arr.length; i++) {
        if (arr[i].id === id) {
            return i;
        }
    }
    return -1;
}

function deleteAuthor(state: AppState, id: string): AppState {
    const authors = state.authors
    const index = getIndex(authors, id)
    const newAuthors = []
    for (let i = 0; i < authors.length; i++) {
        if (i != index) {
            newAuthors.push(authors[i])
        }
    }

    const books = state.books
    const newBooks = []
    for (let i = 0; i< books.length; i++){
        if (books[i].author.id != id){
            newBooks.push(books[i])
        }
    }

    return {
        authors: newAuthors,
        genres: state.genres,
        books: newBooks,
        isLoadedGenres: state.isLoadedGenres,
        isLoadedAuthors: state.isLoadedAuthors,
        isLoadedBooks: state.isLoadedBooks,
        errorMessage: "",
        isShowLoginDialog: state.isShowLoginDialog,
        fetchProps: state.fetchProps
    }
}

function addBook(state: AppState, bookDto: BookDto): AppState {
    const authors = state.authors
    const genres = state.genres
    const author = getAuthor(authors, bookDto.authorId)
    const genre = getGenre(genres, bookDto.genreId)
    const book = new Book(bookDto.id, bookDto.name, author, genre, bookDto.year)
    return {
        genres: genres,
        authors: authors,
        books: [...state.books, book],
        isLoadedGenres: state.isLoadedGenres,
        isLoadedAuthors: state.isLoadedAuthors,
        isLoadedBooks: state.isLoadedBooks,
        errorMessage: "",
        isShowLoginDialog: state.isShowLoginDialog,
        fetchProps: state.fetchProps
    }
}

function updateBook(state: AppState, row: BookRow): AppState {
    const authors = state.authors
    const genres = state.genres
    const books = state.books

    const author = getAuthor(authors, row.author as unknown as string)
    const genre = getGenre(genres, row.genre as unknown as string)

    const bookIndex = getIndex(books, row.book.id)
    const book = books[bookIndex]
    book.year = Number.parseInt(row.year as unknown as string)
    book.name = row.name
    book.author = author
    book.genre = genre

    return {
        genres: genres,
        authors: authors,
        books: [...books],
        isLoadedGenres: state.isLoadedGenres,
        isLoadedAuthors: state.isLoadedAuthors,
        isLoadedBooks: state.isLoadedBooks,
        errorMessage: "",
        isShowLoginDialog: state.isShowLoginDialog,
        fetchProps: state.fetchProps
    }
}

function getAuthor(authors: Author[], id: string): Author {
    const authorIndex = getIndex(authors, id)
    return authors[authorIndex]
}

function getGenre(genres: Genre[], id: string): Genre {
    const genreIndex = getIndex(genres, id)
    return genres[genreIndex]
}

function deleteBook(state: AppState, id: string): AppState {
    const books = state.books
    const index = getIndex(books, id)
    const newBooks = []
    for (let i = 0; i < books.length; i++) {
        if (i != index) {
            newBooks.push(books[i])
        }
    }
    return {...state, books: newBooks}
}