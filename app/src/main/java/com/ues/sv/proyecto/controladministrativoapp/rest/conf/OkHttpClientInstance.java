package com.ues.sv.proyecto.controladministrativoapp.rest.conf;

import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.AlumnoRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.CicloRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.CoordinadorRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.CursoRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.DetalleRevisionRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.DocenteRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.EncargadoImpresionRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.EvaluacionRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.ImagenRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.ImpresionRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.InscripcionRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.MateriaRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.MotivoErrorImpresionRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.ParametrosRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.ParticipanteRevisionRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.PersonaRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.RevisionRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.SolicitudRevisionRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.TipoEvaluacionRestServiceInterface;
import com.ues.sv.proyecto.controladministrativoapp.rest.interfaces.UsuarioRestServiceInterface;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;

public class OkHttpClientInstance {

    private static volatile OkHttpClient OkHtttpInstance;
    private static volatile AlumnoRestServiceInterface alumnoRestServiceInstance;
    private static volatile CicloRestServiceInterface cicloRestServiceInterface;
    private static volatile CoordinadorRestServiceInterface coordinadorRestServiceInterface;
    private static volatile CursoRestServiceInterface cursoRestServiceInterface;
    private static volatile DetalleRevisionRestServiceInterface detalleRevisionRestServiceInterface;
    private static volatile DocenteRestServiceInterface docenteRestServiceInterface;
    private static volatile EncargadoImpresionRestServiceInterface encargadoImpresionRestServiceInterface;
    private static volatile EvaluacionRestServiceInterface evaluacionRestServiceInterface;
    private static volatile ImagenRestServiceInterface imagenRestServiceInterface;
    private static volatile ImpresionRestServiceInterface impresionRestServiceInterface;
    private static volatile InscripcionRestServiceInterface inscripcionRestServiceInterface;
    private static volatile MateriaRestServiceInterface materiaRestServiceInterface;
    private static volatile MotivoErrorImpresionRestServiceInterface motivoErrorImpresionRestServiceInterface;
    private static volatile ParametrosRestServiceInterface parametrosRestServiceInterface;
    private static volatile ParticipanteRevisionRestServiceInterface participanteRevisionRestServiceInterface;
    private static volatile PersonaRestServiceInterface personaRestServiceInterface;
    private static volatile RevisionRestServiceInterface revisionRestServiceInterface;
    private static volatile SolicitudRevisionRestServiceInterface solicitudRevisionRestServiceInterface;
    private static volatile TipoEvaluacionRestServiceInterface tipoEvaluacionRestServiceInterface;
    private static volatile UsuarioRestServiceInterface usuarioRestServiceInterface;


    public static OkHttpClient getOkHtttpInstance() {
        if (OkHtttpInstance == null) {
            synchronized (OkHttpClient.class) {
                if (OkHtttpInstance == null) {
                    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    OkHtttpInstance = new OkHttpClient.Builder().addInterceptor(interceptor).build();
                }
            }
        }
        return OkHtttpInstance;
    }


    public static Retrofit.Builder getRetrofitBuilderInstance() {
        return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(OkHttpClientInstance.getOkHtttpInstance());
    }


    public static AlumnoRestServiceInterface alumnoRestService() {
        if (alumnoRestServiceInstance == null) {
            synchronized (AlumnoRestServiceInterface.class) {
                if (alumnoRestServiceInstance == null) {
                    alumnoRestServiceInstance = OkHttpClientInstance.getRetrofitBuilderInstance().baseUrl(AlumnoRestServiceInterface.BASE_URL).build()
                            .create(AlumnoRestServiceInterface.class);
                }
            }
        }
        return alumnoRestServiceInstance;
    }

    public static CicloRestServiceInterface cicloRestServiceInterface() {
        if (cicloRestServiceInterface == null) {
            synchronized (CicloRestServiceInterface.class) {
                if (cicloRestServiceInterface == null) {
                    cicloRestServiceInterface = OkHttpClientInstance.getRetrofitBuilderInstance().baseUrl(CicloRestServiceInterface.BASE_URL).build()
                            .create(CicloRestServiceInterface.class);
                }
            }
        }
        return cicloRestServiceInterface;
    }

    public static CoordinadorRestServiceInterface coordinadorRestServiceInterface() {
        if (coordinadorRestServiceInterface == null) {
            synchronized (CoordinadorRestServiceInterface.class) {
                if (coordinadorRestServiceInterface == null) {
                    coordinadorRestServiceInterface = OkHttpClientInstance.getRetrofitBuilderInstance().baseUrl(CoordinadorRestServiceInterface.BASE_URL).build()
                            .create(CoordinadorRestServiceInterface.class);
                }
            }
        }
        return coordinadorRestServiceInterface;
    }

    public static CursoRestServiceInterface cursoRestServiceInterface() {
        if (cursoRestServiceInterface == null) {
            synchronized (CursoRestServiceInterface.class) {
                if (cursoRestServiceInterface == null) {
                    cursoRestServiceInterface = OkHttpClientInstance.getRetrofitBuilderInstance().baseUrl(CursoRestServiceInterface.BASE_URL).build()
                            .create(CursoRestServiceInterface.class);
                }
            }
        }
        return cursoRestServiceInterface;
    }

    public static DetalleRevisionRestServiceInterface detalleRevisionRestServiceInterface() {
        if (detalleRevisionRestServiceInterface == null) {
            synchronized (DetalleRevisionRestServiceInterface.class) {
                if (detalleRevisionRestServiceInterface == null) {
                    detalleRevisionRestServiceInterface = OkHttpClientInstance.getRetrofitBuilderInstance().baseUrl(DetalleRevisionRestServiceInterface.BASE_URL).build()
                            .create(DetalleRevisionRestServiceInterface.class);
                }
            }
        }
        return detalleRevisionRestServiceInterface;
    }

    public static DocenteRestServiceInterface docenteRestServiceInterface() {
        if (docenteRestServiceInterface == null) {
            synchronized (DocenteRestServiceInterface.class) {
                if (docenteRestServiceInterface == null) {
                    docenteRestServiceInterface = OkHttpClientInstance.getRetrofitBuilderInstance().baseUrl(DocenteRestServiceInterface.BASE_URL).build()
                            .create(DocenteRestServiceInterface.class);
                }
            }
        }
        return docenteRestServiceInterface;
    }

    public static EncargadoImpresionRestServiceInterface encargadoImpresionRestServiceInterface() {
        if (encargadoImpresionRestServiceInterface == null) {
            synchronized (EncargadoImpresionRestServiceInterface.class) {
                if (encargadoImpresionRestServiceInterface == null) {
                    encargadoImpresionRestServiceInterface = OkHttpClientInstance.getRetrofitBuilderInstance().baseUrl(EncargadoImpresionRestServiceInterface.BASE_URL).build()
                            .create(EncargadoImpresionRestServiceInterface.class);
                }
            }
        }
        return encargadoImpresionRestServiceInterface;
    }

    public static EvaluacionRestServiceInterface evaluacionRestServiceInterface() {
        if (evaluacionRestServiceInterface == null) {
            synchronized (EvaluacionRestServiceInterface.class) {
                if (evaluacionRestServiceInterface == null) {
                    evaluacionRestServiceInterface = OkHttpClientInstance.getRetrofitBuilderInstance().baseUrl(EvaluacionRestServiceInterface.BASE_URL).build()
                            .create(EvaluacionRestServiceInterface.class);
                }
            }
        }
        return evaluacionRestServiceInterface;
    }

    public static ImagenRestServiceInterface imagenRestServiceInterface() {
        if (imagenRestServiceInterface == null) {
            synchronized (ImagenRestServiceInterface.class) {
                if (imagenRestServiceInterface == null) {
                    imagenRestServiceInterface = OkHttpClientInstance.getRetrofitBuilderInstance().baseUrl(ImagenRestServiceInterface.BASE_URL).build()
                            .create(ImagenRestServiceInterface.class);
                }
            }
        }
        return imagenRestServiceInterface;
    }

    public static ImpresionRestServiceInterface impresionRestServiceInterface() {
        if (impresionRestServiceInterface == null) {
            synchronized (ImpresionRestServiceInterface.class) {
                if (impresionRestServiceInterface == null) {
                    impresionRestServiceInterface = OkHttpClientInstance.getRetrofitBuilderInstance().baseUrl(ImpresionRestServiceInterface.BASE_URL).build()
                            .create(ImpresionRestServiceInterface.class);
                }
            }
        }
        return impresionRestServiceInterface;
    }

    public static InscripcionRestServiceInterface inscripcionRestServiceInterface() {
        if (inscripcionRestServiceInterface == null) {
            synchronized (InscripcionRestServiceInterface.class) {
                if (inscripcionRestServiceInterface == null) {
                    inscripcionRestServiceInterface = OkHttpClientInstance.getRetrofitBuilderInstance().baseUrl(InscripcionRestServiceInterface.BASE_URL).build()
                            .create(InscripcionRestServiceInterface.class);
                }
            }
        }
        return inscripcionRestServiceInterface;
    }

    public static MateriaRestServiceInterface materiaRestServiceInterface() {
        if (materiaRestServiceInterface == null) {
            synchronized (MateriaRestServiceInterface.class) {
                if (materiaRestServiceInterface == null) {
                    materiaRestServiceInterface = OkHttpClientInstance.getRetrofitBuilderInstance().baseUrl(MateriaRestServiceInterface.BASE_URL).build()
                            .create(MateriaRestServiceInterface.class);
                }
            }
        }
        return materiaRestServiceInterface;
    }

    public static MotivoErrorImpresionRestServiceInterface motivoErrorImpresionRestServiceInterface() {
        if (motivoErrorImpresionRestServiceInterface == null) {
            synchronized (MotivoErrorImpresionRestServiceInterface.class) {
                if (motivoErrorImpresionRestServiceInterface == null) {
                    motivoErrorImpresionRestServiceInterface = OkHttpClientInstance.getRetrofitBuilderInstance().baseUrl(MotivoErrorImpresionRestServiceInterface.BASE_URL).build()
                            .create(MotivoErrorImpresionRestServiceInterface.class);
                }
            }
        }
        return motivoErrorImpresionRestServiceInterface;
    }

    public static ParametrosRestServiceInterface parametrosRestServiceInterface() {
        if (parametrosRestServiceInterface == null) {
            synchronized (ParametrosRestServiceInterface.class) {
                if (parametrosRestServiceInterface == null) {
                    parametrosRestServiceInterface = OkHttpClientInstance.getRetrofitBuilderInstance().baseUrl(ParametrosRestServiceInterface.BASE_URL).build()
                            .create(ParametrosRestServiceInterface.class);
                }
            }
        }
        return parametrosRestServiceInterface;
    }

    public static ParticipanteRevisionRestServiceInterface participanteRevisionRestServiceInterface() {
        if (participanteRevisionRestServiceInterface == null) {
            synchronized (ParticipanteRevisionRestServiceInterface.class) {
                if (participanteRevisionRestServiceInterface == null) {
                    participanteRevisionRestServiceInterface = OkHttpClientInstance.getRetrofitBuilderInstance().baseUrl(ParticipanteRevisionRestServiceInterface.BASE_URL).build()
                            .create(ParticipanteRevisionRestServiceInterface.class);
                }
            }
        }
        return participanteRevisionRestServiceInterface;
    }

    public static PersonaRestServiceInterface personaRestServiceInterface() {
        if (personaRestServiceInterface == null) {
            synchronized (PersonaRestServiceInterface.class) {
                if (personaRestServiceInterface == null) {
                    personaRestServiceInterface = OkHttpClientInstance.getRetrofitBuilderInstance().baseUrl(PersonaRestServiceInterface.BASE_URL).build()
                            .create(PersonaRestServiceInterface.class);
                }
            }
        }
        return personaRestServiceInterface;
    }

    public static RevisionRestServiceInterface revisionRestServiceInterface() {
        if (revisionRestServiceInterface == null) {
            synchronized (RevisionRestServiceInterface.class) {
                if (revisionRestServiceInterface == null) {
                    revisionRestServiceInterface = OkHttpClientInstance.getRetrofitBuilderInstance().baseUrl(RevisionRestServiceInterface.BASE_URL).build()
                            .create(RevisionRestServiceInterface.class);
                }
            }
        }
        return revisionRestServiceInterface;
    }

    public static SolicitudRevisionRestServiceInterface solicitudRevisionRestServiceInterface() {
        if (solicitudRevisionRestServiceInterface == null) {
            synchronized (SolicitudRevisionRestServiceInterface.class) {
                if (solicitudRevisionRestServiceInterface == null) {
                    solicitudRevisionRestServiceInterface = OkHttpClientInstance.getRetrofitBuilderInstance().baseUrl(SolicitudRevisionRestServiceInterface.BASE_URL).build()
                            .create(SolicitudRevisionRestServiceInterface.class);
                }
            }
        }
        return solicitudRevisionRestServiceInterface;
    }

    public static TipoEvaluacionRestServiceInterface tipoEvaluacionRestServiceInterface() {
        if (tipoEvaluacionRestServiceInterface == null) {
            synchronized (TipoEvaluacionRestServiceInterface.class) {
                if (tipoEvaluacionRestServiceInterface == null) {
                    tipoEvaluacionRestServiceInterface = OkHttpClientInstance.getRetrofitBuilderInstance().baseUrl(TipoEvaluacionRestServiceInterface.BASE_URL).build()
                            .create(TipoEvaluacionRestServiceInterface.class);
                }
            }
        }
        return tipoEvaluacionRestServiceInterface;
    }

    public static UsuarioRestServiceInterface usuarioRestServiceInterface() {
        if (usuarioRestServiceInterface == null) {
            synchronized (UsuarioRestServiceInterface.class) {
                if (usuarioRestServiceInterface == null) {
                    usuarioRestServiceInterface = OkHttpClientInstance.getRetrofitBuilderInstance().baseUrl(UsuarioRestServiceInterface.BASE_URL).build()
                            .create(UsuarioRestServiceInterface.class);
                }
            }
        }
        return usuarioRestServiceInterface;
    }

}
