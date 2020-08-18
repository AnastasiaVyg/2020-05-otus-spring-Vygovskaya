import React from 'react';
import MaterialTable, {Column} from 'material-table';
import {Author} from "../model/Author";
import {useDispatch, useSelector} from "react-redux";
import {ADD_AUTHOR, DELETE_AUTHOR, UPDATE_AUTHOR} from "../store/ActionConsts";
import {AppState} from "../store/Storable";
import CircularIndeterminate from "./Loader";
import {addAuthor, deleteAuthor, loadAuthors, updateAuthor} from "../store/Actions";

export interface AuthorRow {
    name: string;
    surname: string;
    author: Author
}

const columns = [
            {title: 'Name', field: 'name'},
            {title: 'Surname', field: 'surname'},
]

export function AuthorTable() {
    const isLoaded = useSelector((state: AppState) => state.isLoadedAuthors)
    const authors =  useSelector((state :AppState)  => {return state.authors})
    const dispatch = useDispatch()

    if (!isLoaded){
        loadAuthors(dispatch)
        return (
            <CircularIndeterminate/>
        )
    }

    const authorRows: AuthorRow[] = authors.map(author => {
        return {
            name: author.name,
            surname: author.surname,
            author: author
        }
    })

    console.log("state authors", authors)

    return (
        <MaterialTable
            title="Authors"
            columns={columns}
            data={authorRows}
            options={{
                actionsColumnIndex: -1
            }}
            editable={{
                onRowAdd: (newData) => {
                    addAuthor(dispatch, newData.name, newData.surname)
                    return Promise.resolve()
                },
                onRowUpdate: (newData, oldData) => {
                    updateAuthor(dispatch, newData)
                    return Promise.resolve()
                },
                onRowDelete: (oldData) => {
                    deleteAuthor(dispatch, oldData.author.id)
                    return Promise.resolve()
                }
            }}
        />
    );
}
