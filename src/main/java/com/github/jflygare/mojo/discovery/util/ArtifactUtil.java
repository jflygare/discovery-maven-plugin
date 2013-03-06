package com.github.jflygare.mojo.discovery.util;

import java.util.ArrayList;
import java.util.List;

import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.util.artifact.DefaultArtifact;
import org.sonatype.aether.util.artifact.JavaScopes;

public class ArtifactUtil {

	public static Artifact toAetherArtifact(org.apache.maven.artifact.Artifact artifact) {
		return new DefaultArtifact(key(artifact));
	}

    public static String key( Artifact artifact )
    {
        return key( artifact.getGroupId(), artifact.getArtifactId(), artifact.getClassifier(), artifact.getVersion() );
    }

    public static String key( org.apache.maven.artifact.Artifact artifact )
    {
        return key( artifact.getGroupId(), artifact.getArtifactId(), artifact.getClassifier(), artifact.getVersion() );
    }

    public static String key( String groupId, String artifactId, String classifier, String version )
    {
        if ( groupId == null )
        {
            throw new NullPointerException( "groupId is null" );
        }
        if ( artifactId == null )
        {
            throw new NullPointerException( "artifactId is null" );
        }
        if ( version == null )
        {
            throw new NullPointerException( "version is null" );
        }

        return groupId + ":" + artifactId + ((classifier != null) ? ":" + classifier : "") + ":" + version;
    }

    public static List<String> dependencyScopes() {
    	List<String> scopes = new ArrayList<String>();
    	scopes.add(JavaScopes.COMPILE);
    	scopes.add(JavaScopes.RUNTIME);
    	scopes.add(JavaScopes.TEST);
    	scopes.add(JavaScopes.SYSTEM);
    	scopes.add(JavaScopes.PROVIDED);
    	return scopes;
    }

}
