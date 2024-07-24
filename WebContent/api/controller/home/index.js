'use strict';

import { footer } from "./../../lib/commons.js";
import { control } from "./../../controller/web.auth.js";

let me;
(async () => {
    me = control(true, []); 
})();