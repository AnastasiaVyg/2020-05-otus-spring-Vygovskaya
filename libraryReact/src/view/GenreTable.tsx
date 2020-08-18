import React from 'react';
import MaterialTable, { Column } from 'material-table';
import {Genre} from "../model/Genre";
import {useDispatch, useSelector} from "react-redux";
import {AppState} from "../store/Storable";
import CircularIndeterminate from "./Loader";
import {addGenre, deleteGenre, loadGenres, updateGenre} from "../store/Actions";

export interface GenreRow {
    name: string;
    genre: Genre
}

const columns = [
    {title: 'Name', field: 'name'},
]

export function GenreTable() {
    const isLoaded = useSelector((state: AppState) => state.isLoadedGenres)
    const genres = useSelector((state: AppState) => state.genres)
    const dispatch = useDispatch()

    if (!isLoaded){
        loadGenres(dispatch)
        return (
            <CircularIndeterminate/>
        )
    }

    const genreRows: GenreRow[] = genres.map(genre => {
        return {
            name: genre.name,
            genre: genre
        }
    })

    return (
        <MaterialTable
            title="Genres"
            columns={columns}
            data={genreRows}
            options={{
             actionsColumnIndex:-1
            }}
            editable={{
                onRowAdd: (newData) =>{
                    addGenre(dispatch, newData.name)
                    return Promise.resolve()
                },
                onRowUpdate: (newData, oldData) =>{
                    updateGenre(dispatch, newData)
                    return Promise.resolve()
                },
                onRowDelete: (oldData) =>{
                    deleteGenre(dispatch, oldData.genre.id)
                    return Promise.resolve()
                }
            }}
        />
    );
}