package com.dmytro.lisovyi.earthquakemap;

import com.dmytro.lisovyi.earthquakemap.api.Repository;

public interface ComponentProvider {

    Repository getRepository();

}
