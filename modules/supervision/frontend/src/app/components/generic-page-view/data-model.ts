import { SortedPageParam } from "@lagoshny/ngx-hateoas-client/lib/model/declarations";
import { Observable } from "rxjs";

export interface ViewRoute {
    readonly displayName: string;
    readonly applicationRoute: string;
}

export interface GenericPageViewDataRepresentationLink {
    displayName: string;
    link: string;
    icon?: string;
}

type GenericPageViewDataRepresentationAttributes = { [attr: string]: string | number | null | undefined };
type GenericPageViewDataRepresentationSortableAttributes = { [attr: string]: string };  // Object attribute -> sort attribute

export interface GenericPageViewDataRepresentation {
    readonly name: string;
    readonly description: string;
    readonly statisticsLink?: string;
    readonly attributes: GenericPageViewDataRepresentationAttributes;
    readonly mainLink: GenericPageViewDataRepresentationLink;
    readonly fullPageDisplayLink: GenericPageViewDataRepresentationLink;
    readonly otherLinks: readonly GenericPageViewDataRepresentationLink[];
}

export interface GenericPageViewDataCollectionRepresentation {
    readonly resources: readonly GenericPageViewDataRepresentation[];
    readonly totalElements: number;
    readonly totalPages: number;
    readonly sorteableAttributes?: GenericPageViewDataRepresentationSortableAttributes;
}

export type PagedValuesProvider = (sortedPageParam: SortedPageParam) => Observable<GenericPageViewDataCollectionRepresentation>;
