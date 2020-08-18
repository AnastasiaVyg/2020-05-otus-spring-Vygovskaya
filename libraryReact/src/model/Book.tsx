import {Author} from "./Author";
import {Genre} from "./Genre";

export class Book {
    static readonly empty = new Book("-1", "", Author.empty, Genre.empty, -1)
    private _name: string
    private _year: number
    private _author: Author
    private _genre: Genre
    private _comments: Array<string>
    private _isLoadedComments: boolean

    constructor(readonly id: string, name: string, author: Author, genre: Genre, year: number) {
        this._name = name
        this._year = year
        this._author = author
        this._genre = genre
        this._comments = []
        this._isLoadedComments = false
    }

    get name(): string {
        return this._name;
    }

    set name(value: string) {
        this._name = value;
    }

    get year(): number {
        return this._year;
    }

    set year(value: number) {
        this._year = value;
    }

    get author(): Author {
        return this._author;
    }

    set author(value: Author) {
        this._author = value;
    }

    get genre(): Genre {
        return this._genre;
    }

    set genre(value: Genre) {
        this._genre = value;
    }

    get comments(): Array<string> {
        return this._comments;
    }

    set comments(value: Array<string>) {
        this._comments = value;
    }

    addComment(value: string) {
        this._comments.push(value);
    }

    get isLoadedComments(): boolean {
        return this._isLoadedComments;
    }

    set isLoadedComments(value: boolean) {
        this._isLoadedComments = value;
    }
}

export class BookDto {
    constructor(readonly id: string, readonly name: string, readonly authorId: string, readonly genreId: string, readonly year: number) {
    }

}