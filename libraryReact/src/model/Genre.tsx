
export class Genre {
    static readonly empty = new Genre("-1", "")
    private _name: string

    constructor(readonly id: string, name: string) {
         this._name = name
    }

    get name(): string {
        return this._name;
    }

    set name(value: string) {
        this._name = value;
    }
}
