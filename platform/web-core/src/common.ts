// eslint-disable-next-line import/prefer-default-export,max-classes-per-file
export interface HasClassName {
  className: string;
}

export type ErrorHandler = {
  // eslint-disable-next-line no-unused-vars
  onError: (er: any) => void
}
