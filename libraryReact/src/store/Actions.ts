import {Dispatch} from "react";
import {Genre} from "../model/Genre";
import {
    ADD_AUTHOR, ADD_BOOK, ADD_COMMENT,
    ADD_GENRE, DELETE_AUTHOR, DELETE_BOOK,
    DELETE_GENRE,
    LOAD_AUTHORS,
    LOAD_BOOKS, LOAD_COMMENTS,
    LOAD_GENRES, SET_ERROR_MESSAGE,
    UPDATE_AUTHOR, UPDATE_BOOK,
    UPDATE_GENRE
} from "./ActionConsts";
import {Author} from "../model/Author";
import {BookDto} from "../model/Book";
import {GenreRow} from "../view/GenreTable";
import {AuthorRow} from "../view/AuthorTable";
import {BookRow} from "../view/BookTable";

const GENRES_URL = '/genres'
const AUTHORS_URL = '/authors'
const BOOKS_URL = '/books'

export function loadGenres(dispatch: Dispatch<any>) {
    fetch(GENRES_URL).then(response => {
        if (response.ok){
            response.json().then(data => {
                const genres = data as Array<Genre>
                dispatch({type: LOAD_GENRES, data: genres})
            })
        }}).catch(e => {
        dispatch({type: SET_ERROR_MESSAGE, message: "Ошибка при загрузке данных"})
    })
}

export function loadAuthors(dispatch: Dispatch<any>) {
    fetch(AUTHORS_URL).then(response => {
        if (response.ok){
            response.json().then(data => {
                const authors = data as Array<Author>
                dispatch({type: LOAD_AUTHORS, data: authors})
            })
        }}).catch(e => {
        dispatch({type: SET_ERROR_MESSAGE, message: "Ошибка при загрузке данных"})
    })
}

export function loadBooks(dispatch: Dispatch<any>) {
    fetch(BOOKS_URL).then(response => {
        if (response.ok){
            response.json().then(data => {
                const books = data as Array<BookDto>
                dispatch({type: LOAD_BOOKS, data: books})
            })
        }}).catch(e => {
        dispatch({type: SET_ERROR_MESSAGE, message: "Ошибка при загрузке данных"})
    })
}

export function addGenre(dispatch: Dispatch<any>, name: string) {
    fetch(GENRES_URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({name: name})
    }).then(response => {
        if (response.ok) {
            response.json().then(data => {
                    const genre = data as Genre
                    dispatch({type: ADD_GENRE, data: genre})
                }
            )
        }}).catch(e => {
            dispatch({type: SET_ERROR_MESSAGE, message: "Ошибка при добавлении жанра"})
    })

}

export function updateGenre(dispatch: Dispatch<any>, genreRow: GenreRow) {
    fetch(GENRES_URL + "/" + genreRow.genre.id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({name: genreRow.name})
    }).then(response => {
        if (response.ok) {
            response.json().then(data => {
                if (data === true) {
                    dispatch({type: UPDATE_GENRE, row: genreRow})
                }
            })
        }}).catch(e => {
        dispatch({type: SET_ERROR_MESSAGE, message: "Ошибка при обновлении жанра"})
    })
}

export function deleteGenre(dispatch: Dispatch<any>, id: string) {
    fetch(GENRES_URL + "/" + id, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        }
    }).then(responce => {
        if (responce.ok) {
            dispatch({type: DELETE_GENRE, data: id})
        }
    }).catch(e => {
        dispatch({type: SET_ERROR_MESSAGE, message: "Ошибка при удалении жанра"})
    })
}

export function addAuthor(dispatch: Dispatch<any>, name: string, surname: string) {
    fetch(AUTHORS_URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({name: name, surname: surname})
    }).then(response => {
        if (response.ok){
            response.json().then(data => {
                const author = data as Author
                dispatch({type: ADD_AUTHOR, data: author})
            })
        }}).catch(e => {
        dispatch({type: SET_ERROR_MESSAGE, message: "Ошибка при добавлении автора"})
    })
}

export function updateAuthor(dispatch: Dispatch<any>, authorRow: AuthorRow) {
    fetch(AUTHORS_URL + "/" + authorRow.author.id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({name: authorRow.name, surname: authorRow.surname})
    }).then(response => {
        if (response.ok){
            response.json().then(data => {
                if (data === true) {
                    dispatch({type: UPDATE_AUTHOR, row: authorRow})
                }
            })
        }}).catch(e => {
        dispatch({type: SET_ERROR_MESSAGE, message: "Ошибка при обновлении автора"})
    })
}

export function deleteAuthor(dispatch: Dispatch<any>, id: string) {
    fetch(AUTHORS_URL + "/" + id, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        }
    }).then(responce => {
        if (responce.ok) {
            dispatch({type: DELETE_AUTHOR, data: id})
        }
    }).catch(e => {
        dispatch({type: SET_ERROR_MESSAGE, message: "Ошибка при удалении автора"})
    })
}

export function addBook(dispatch: Dispatch<any>, bookRow: BookRow) {
    fetch(BOOKS_URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({name: bookRow.name, authorId: bookRow.author, genreId: bookRow.genre, year: bookRow.year})
    }).then(response => {
        if (response.ok) {
            response.json().then(data => {
                const bookDto = data as BookDto
                dispatch({type: ADD_BOOK, data: bookDto})
            })
        }}).catch(e => {
        dispatch({type: SET_ERROR_MESSAGE, message: "Ошибка при добавлении книги"})
    })
}

export function updateBook(dispatch: Dispatch<any>, bookRow: BookRow) {
    fetch(BOOKS_URL + "/" + bookRow.book.id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({name: bookRow.name, authorId: bookRow.author, genreId: bookRow.genre, year: bookRow.year})
    }).then(response => {
        if (response.ok) {
            response.json().then(data => {
                if (data === true) {
                    dispatch({type: UPDATE_BOOK, row: bookRow})
                }
            })
        }}).catch(e => {
        dispatch({type: SET_ERROR_MESSAGE, message: "Ошибка при обновлении книги"})
    })
}

export function deleteBook(dispatch: Dispatch<any>, id: string) {
    fetch(BOOKS_URL + "/" + id, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        }
    }).then(responce => {
        if (responce.ok) {
            dispatch({type: DELETE_BOOK, data: id})
        }
    }).catch(e => {
        dispatch({type: SET_ERROR_MESSAGE, message: "Ошибка при удалении книги"})
    })
}

export function loadComments(dispatch: Dispatch<any>, id: string) {
    fetch(BOOKS_URL + "/" + id + "/comments")
        .then(response => {
            if (response.ok) {
                response.json().then(data => {
                    const comments = data as Array<string>
                    dispatch({type: LOAD_COMMENTS, comments: comments, id: id})
            })
        }}).catch(e => {
        dispatch({type: SET_ERROR_MESSAGE, message: "Ошибка при загрузке комментариев"})
    })
}

export function addComment(dispatch: Dispatch<any>, bookId: string, comment: string) {
    fetch(BOOKS_URL + "/" + bookId + "/comments", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({comment: comment})
    }).then(response => {
        if (response.ok){
            response.json().then(data => {
                if (data === true) {
                    dispatch({type: ADD_COMMENT, comment: comment, id: bookId})
                }
            })
        }}).catch(e => {
        dispatch({type: SET_ERROR_MESSAGE, message: "Ошибка при добавлении комментария"})
    })
}