import { AbstractMapper } from './mapper';

export class BasicMapper<T> extends AbstractMapper<T, string> {

    public static forField<T>(field: string): BasicMapper<T> {
        return new BasicMapper(field);
    }

    private constructor(field: string) {
        super(field);
    }


    public map<V extends object>(data: Array<object>): Array<T> {
        
        /*
        return data
        .filter(v => Boolean(v))
        .filter(v => v.keys().indexof(this.field))
        .map(v => v[this.field]);
        */
       return [];
    }
    
}
