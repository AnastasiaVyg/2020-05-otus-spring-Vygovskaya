export class Author {
    static readonly empty = new Author("-1", "", "")
    private _name: string
    private _surname: string

    constructor(readonly id: string, name: string, surname: string) {
        this.id = id
        this._name = name
        this._surname = surname
    }

    get name(): string {
        return this._name;
    }

    set name(value: string) {
        this._name = value;
    }

    get surname(): string {
        return this._surname;
    }

    set surname(value: string) {
        this._surname = value;
    }
}
