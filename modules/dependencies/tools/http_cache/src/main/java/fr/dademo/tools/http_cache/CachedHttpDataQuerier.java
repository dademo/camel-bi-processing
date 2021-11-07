package fr.dademo.tools.http_cache;

import fr.dademo.tools.http.repository.DefaultHttpDataQuerier;
import fr.dademo.tools.http.repository.HttpDataQuerier;
import org.springframework.stereotype.Repository;

@Repository
public class CachedHttpDataQuerier extends DefaultHttpDataQuerier implements HttpDataQuerier {

}
