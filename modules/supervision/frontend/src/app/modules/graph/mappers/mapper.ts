export type MapperField = string;

// TODO
export abstract class AbstractMapper<T, F> {

    constructor(protected readonly field: F) { }

    public abstract map<V extends object>(data: Array<V>): Array<T>;
}