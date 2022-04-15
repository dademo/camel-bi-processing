import { SharedModule } from './shared/shared.module';
import { GraphModule } from './graph/graph.module';
import { BackendModule } from './backend/backend.module';

function getAllAppModules(): Array<any> {
    return [
        SharedModule,
        GraphModule,
        BackendModule,
    ];
}

export {
    SharedModule,
    GraphModule,
    BackendModule,
    getAllAppModules,
};
