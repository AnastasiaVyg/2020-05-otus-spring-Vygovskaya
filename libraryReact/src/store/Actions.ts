import {Dispatch} from "react";
import {Genre} from "../model/Genre";
import {
    ADD_AUTHOR, ADD_BOOK, ADD_COMMENT,
    ADD_GENRE, DELETE_AUTHOR, DELETE_BOOK,
    DELETE_GENRE,
    LOAD_AUTHORS,
    LOAD_BOOKS, LOAD_COMMENTS,
    LOAD_GENRES, SHOW_LOGIN_DIALOG, SET_ERROR_MESSAGE,
    UPDATE_AUTHOR, UPDATE_BOOK,
    UPDATE_GENRE, CLEAR_DATA
} from "./ActionConsts";
import {Author} from "../model/Author";
import {BookDto} from "../model/Book";
import {GenreRow} from "../view/GenreTable";
import {AuthorRow} from "../view/AuthorTable";
import {BookRow} from "../view/BookTable";

const GENRES_URL = '/genres'
const AUTHORS_URL = '/authors'
const BOOKS_URL = '/books'

export interface FetchProps {
    url: string
    method: string
    body: string
    responseFunc: { (r: Response): void }
}

function baseFetch(dispatch: Dispatch<any>, props: FetchProps) {
    let responseHandle = (response: Response) => {
        if (response.ok){
            props.responseFunc(response)
        }
        if (response.status === 401) {
            dispatch({type: SHOW_LOGIN_DIALOG, data: true, fetchProps: props})
        }
        if (response.status === 403) {
            dispatch({type: SET_ERROR_MESSAGE, message: "Ошибка авторизации"})
        }
    }
    let errorHandle = (e: any) => dispatch({type: SET_ERROR_MESSAGE, message: "Ошибка при загрузке данных"})

    if (props.body === "")
        fetch(props.url, {
            method: props.method,
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            }
        }).then(responseHandle).catch(errorHandle)
    else
        fetch(props.url, {
            method: props.method,
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: props.body
        }).then(responseHandle).catch(errorHandle)
}

export function loginFetch(dispatch: Dispatch<any>, login: String, password: String, props: FetchProps) {

    fetch("/login", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        credentials: "include",
        redirect: "manual",
        body: "username=" + login +"&password=" + password
    }).then(response => {
        console.log(response.status)
        dispatch({type: SHOW_LOGIN_DIALOG, data: false})
        if (response.ok){
            console.log(response.url)
            baseFetch(dispatch, props)
        } else {
            dispatch({type: SHOW_LOGIN_DIALOG, data: true})
        }
    })
}

export function logoutFetch(dispatch: Dispatch<any>) {
    fetch("/logout", {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        redirect: "manual"
    }).then(response => {
        dispatch({type: SHOW_LOGIN_DIALOG, data: true})
        dispatch({type: CLEAR_DATA})
    })
}

export function loadGenres(dispatch: Dispatch<any>) {
    let props = {
        url: GENRES_URL,
        method: 'GET',
        body: "",
        responseFunc: (response: Response) => {
            if (response.ok) {
                response.json().then(data => {
                    const genres = data as Array<Genre>
                    dispatch({type: LOAD_GENRES, data: genres})
                })
            }
        }
    }
    baseFetch(dispatch, props)
}

export function loadAuthors(dispatch: Dispatch<any>) {
    let props = {
        url: AUTHORS_URL,
        method: 'GET',
        body: "",
        responseFunc: (response: Response) => {
            if (response.ok) {
                response.json().then(data => {
                    const authors = data as Array<Author>
                    dispatch({type: LOAD_AUTHORS, data: authors})
                })
            }
        }
    }
    baseFetch(dispatch, props)
}

export function loadBooks(dispatch: Dispatch<any>) {
    let props = {
        url: BOOKS_URL,
        method: 'GET',
        body: "",
        responseFunc: (response: Response) => {
            if (response.ok) {
                response.json().then(data => {
                    const books = data as Array<BookDto>
                    dispatch({type: LOAD_BOOKS, data: books})
                })
            }
        }
    }
    baseFetch(dispatch, props)
}

export function addGenre(dispatch: Dispatch<any>, name: string) {
    let props = {
        url: GENRES_URL,
        method: 'POST',
        body: JSON.stringify({name: name}),
        responseFunc: (response: Response) => {
            if (response.ok) {
                response.json().then(data => {
                    const genre = data as Genre
                    dispatch({type: ADD_GENRE, data: genre})
                })
            }
        }
    }
    baseFetch(dispatch, props)
}

export function updateGenre(dispatch: Dispatch<any>, genreRow: GenreRow) {
    let props = {
        url: GENRES_URL + "/" + genreRow.genre.id,
        method: 'PUT',
        body: JSON.stringify({name: genreRow.name}),
        responseFunc: (response: Response) => {
            if (response.ok) {
                response.json().then(data => {
                    if (data === true) {
                        dispatch({type: UPDATE_GENRE, row: genreRow})
                    }
                })
            }
        }
    }
    baseFetch(dispatch, props)
}

export function deleteGenre(dispatch: Dispatch<any>, id: string) {
    let props = {
        url: GENRES_URL + "/" + id,
        method: 'DELETE',
        body: "",
        responseFunc: (response: Response) => {
            if (response.ok) {
                dispatch({type: DELETE_GENRE, data: id})
            }
        }
    }
    baseFetch(dispatch, props)
}

export function addAuthor(dispatch: Dispatch<any>, name: string, surname: string) {
    let props = {
        url: AUTHORS_URL,
        method: 'POST',
        body: JSON.stringify({name: name, surname: surname}),
        responseFunc: (response: Response) => {
            if (response.ok) {
                response.json().then(data => {
                    const author = data as Author
                    dispatch({type: ADD_AUTHOR, data: author})
                })
            }
        }
    }
    baseFetch(dispatch, props)
}

export function updateAuthor(dispatch: Dispatch<any>, authorRow: AuthorRow) {
    let props = {
        url: AUTHORS_URL + "/" + authorRow.author.id,
        method: 'PUT',
        body: JSON.stringify({name: authorRow.name, surname: authorRow.surname}),
        responseFunc: (response: Response) => {
            if (response.ok) {
                response.json().then(data => {
                    if (data === true) {
                        dispatch({type: UPDATE_AUTHOR, row: authorRow})
                    }
                })
            }
        }
    }
    baseFetch(dispatch, props)
}

export function deleteAuthor(dispatch: Dispatch<any>, id: string) {
    let props = {
        url: AUTHORS_URL + "/" + id,
        method: 'DELETE',
        body: "",
        responseFunc: (response: Response) => {
            if (response.ok) {
                dispatch({type: DELETE_AUTHOR, data: id})
            }
        }
    }
    baseFetch(dispatch, props)
}

export function addBook(dispatch: Dispatch<any>, bookRow: BookRow) {
    let props = {
        url: BOOKS_URL,
        method: 'POST',
        body: JSON.stringify({name: bookRow.name, authorId: bookRow.author, genreId: bookRow.genre, year: bookRow.year}),
        responseFunc: (response: Response) => {
            if (response.ok) {
                response.json().then(data => {
                    const bookDto = data as BookDto
                    dispatch({type: ADD_BOOK, data: bookDto})
                })
            }
        }
    }
    baseFetch(dispatch, props)
}

export function updateBook(dispatch: Dispatch<any>, bookRow: BookRow) {
    let props = {
        url: BOOKS_URL + "/" + bookRow.book.id,
        method: 'PUT',
        body: JSON.stringify({name: bookRow.name, authorId: bookRow.author, genreId: bookRow.genre, year: bookRow.year}),
        responseFunc: (response: Response) => {
            if (response.ok) {
                response.json().then(data => {
                    if (data === true) {
                        dispatch({type: UPDATE_BOOK, row: bookRow})
                    }
                })
            }
        }
    }
    baseFetch(dispatch, props)
}

export function deleteBook(dispatch: Dispatch<any>, id: string) {
    let props = {
        url: BOOKS_URL + "/" + id,
        method: 'DELETE',
        body: "",
        responseFunc: (response: Response) => {
            if (response.ok) {
                dispatch({type: DELETE_BOOK, data: id})
            }
        }
    }
    baseFetch(dispatch, props)
}

export function loadComments(dispatch: Dispatch<any>, id: string) {
    let props = {
        url: BOOKS_URL + "/" + id + "/comments",
        method: 'GET',
        body: "",
        responseFunc: (response: Response) => {
            if (response.ok) {
                response.json().then(data => {
                    const comments = data as Array<string>
                    dispatch({type: LOAD_COMMENTS, comments: comments, id: id})
                })
            }
        }
    }
    baseFetch(dispatch, props)
}

export function addComment(dispatch: Dispatch<any>, bookId: string, comment: string) {
    let props = {
        url: BOOKS_URL + "/" + bookId + "/comments",
        method: 'POST',
        body: JSON.stringify({comment: comment}),
        responseFunc: (response: Response) => {
            if (response.ok) {
                response.json().then(data => {
                    if (data === true) {
                        dispatch({type: ADD_COMMENT, comment: comment, id: bookId})
                    }
                })
            }
        }
    }
    baseFetch(dispatch, props)
}